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


port = 2000

if ARGV.length > 0
 port = ARGV[0].to_i
end

server = TCPServer.open(port) 
puts "server started \nlistening on #{local_ip}"

@last_message

clients = Array.new
Thread.start do
  loop do
    Thread.start(server.accept) do |client|
      puts "client connected"
      client.puts @last_message unless @last_message.nil?
      clients << client
    end
  end
end

loop do
  while input = gets
    break if input.strip == 'start'
  end
   count = 0
    CSV.foreach("KramerOlympics10000.csv") do |row|
      unless count < 2
        sleep row.to_s.split(';')[3].to_f / 5
        @last_message = row.join
        puts row.join
        puts clients.size
        clients.each do |client| 
          client.puts(row.join + "\n")
        end
      end
      count += 1
    end
   clients.each { |client| client.close }
end
