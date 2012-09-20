require 'socket'               # Get sockets from stdlib
require 'csv'

Encoding.default_external = Encoding::UTF_8
Encoding.default_internal = Encoding::UTF_8


def local_ip
  orig = Socket.do_not_reverse_lookup  
  Socket.do_not_reverse_lookup =true # turn off reverse DNS resolution temporarily
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

loop do
 Thread.start(server.accept) do |client|
   puts "client connected"
   count = 0
    CSV.foreach("KramerOlympics10000.csv") do |row|
      sleep(2)
      unless count < 2
        puts row.join
        client.puts(row.join + "\n")
      end
      count += 1
    end
   client.close
 end
end