require 'socket'               # Get sockets from stdlib
require 'csv'

Encoding.default_external = Encoding::UTF_8
Encoding.default_internal = Encoding::UTF_8

port = 2000

if ARGV.length > 0
 port = ARGV[0].to_i
end

server = TCPServer.open(port) 
puts "server started"
loop do
 Thread.start(server.accept) do |client|
   puts "client connected"
   client.puts("oh hai dere")
    CSV.foreach("KramerOlympics10000.csv") do |row|
      puts row.join
    	client.puts(row.join + "\n")
    	sleep(5)
    end
   client.close
 end
end
