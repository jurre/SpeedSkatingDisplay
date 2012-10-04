require 'socket'               # Get sockets from stdlib
require 'csv'
require 'mongo'

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


# here be dragons
def send_lap_data(clients, csv_filename, initial_direction)
  count = 0
  name, lap_data = "", ""
  direction = initial_direction

  CSV.foreach(csv_filename) do |row|
    row = row[0] + ";#{direction}"
    wait_time = row.to_s.split(';')[3].to_f / 5
    name = row.to_s.split(';')[1] if count == 0

    unless count < 2
      sleep wait_time
      @last_message = row
      puts row + " for player #{name}"
      clients.each { |client| send_data(client, row) }
    end
    
    count += 1
    direction = (direction == 'l') ? 'r' : 'l'
    lap_data = row
  end

  row = lap_data.to_s.split(';')
  total_time = row[2]
  time_in_milliseconds = time_to_milliseconds(total_time)
  document = {"name" => format_name(name), "total_time" => total_time, "total_time_in_milliseconds" => time_in_milliseconds }
  @collection.insert(document)
end

def send_data(client, row)
    client.puts(row + "\n") 
  rescue Errno::EPIPE => error
end

def format_name(name)
  name.split(".").each { |n| n.capitalize! }.join(". ")
end

def time_to_milliseconds(total_time)
  minutes = total_time.split(':')[0].to_i
  seconds = total_time.split(':')[1].to_f
  (minutes * 60 * 1000 + seconds * 1000).to_i
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

# mongodb connection
connection = Mongo::Connection.new("127.0.0.1", 2502)
db = connection.db("meteor")
@collection = db.collection("players")


@player_server = TCPServer.open(2000)
@opponent_server = TCPServer.open(3000)
puts "server started \nlistening on #{local_ip} \ntype 'start' to start the match "

@last_message

@player_clients = Array.new
@opponent_clients = Array.new

@disconnected_clients = Array.new

# main loop
loop do

  # accept connections
  accept_connections(@player_clients, @player_server)
  accept_connections(@opponent_clients, @opponent_server)

  # wait untill the server is started
  while input = gets
    break if input.strip == 'start'
    close_connections if input.strip == 'close-connections'
  end

  threads = [].tap do |thread|
    thread << Thread.start { send_lap_data(@player_clients, "KramerOlympics10000.csv", 'l') }
    thread << Thread.start { send_lap_data(@opponent_clients, "SkobrevOlympics10000.csv", 'r') }
  end

  threads.each { |thread| thread.join } # wait for all the data to be sent
end
