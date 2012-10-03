require 'socket'               # Get sockets from stdlib
require 'csv'

Encoding.default_external = Encoding::UTF_8
Encoding.default_internal = Encoding::UTF_8

def local_ip
  orig = Socket.do_not_reverse_lookup  
  Socket.do_not_reverse_lookup = true # turn off reverse DNS resolution temporarily
  UDPSocket.open do |s|
    s.connect '64.233.187.99', 1 #google
    s.addr.last
  end
ensure
  Socket.do_not_reverse_lookup = orig
end

def send_lap_data(clients, csv_filename)
  count = 0
  name = ""
  CSV.foreach(csv_filename) do |row|
    name = row.to_s.split(';')[1] if count == 0
    unless count < 2
      sleep row.to_s.split(';')[3].to_f / 5
      @last_message = row.join
      puts row.join + "for player #{name}"
      clients.each do |client| 
        client.puts(row.join + "\n")
      end
    end
    count += 1
  end
end

def accept_connections(clients_array, server)
  Thread.start do
    loop do
      Thread.start(server.accept) do |client|
        puts "client connected"
        client.puts @last_message unless @last_message.nil? # send the last lapdata as soon as the client connects
        clients_array << client
      end
    end
  end
end

def close_connections
  (@player_clients + @opponent_clients).each { |client| client.close }
  puts "disconnected all clients"
end


@player_server = TCPServer.open(2000)
@opponent_server = TCPServer.open(3000)
puts "server started \nlistening on #{local_ip}"

@last_message

@player_clients = Array.new
@opponent_clients = Array.new

# main loop
loop do

  # wait untill the server is started
  while input = gets
    break if input.strip == 'start'
    close_connections if input.strip == 'close-connections'
  end

  accept_connections(@player_clients, @player_server)
  accept_connections(@opponent_clients, @opponent_server)

  threads = [].tap do |thread|
    thread << Thread.start { send_lap_data(@player_clients, "KramerOlympics10000.csv") }
    thread << Thread.start { send_lap_data(@opponent_clients, "SkobrevOlympics10000.csv") }
  end

  threads.each { |thread| thread.join } # wait for all the data to be sent
end
