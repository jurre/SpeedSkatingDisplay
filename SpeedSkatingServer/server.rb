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
   count = 0
    CSV.foreach("KramerOlympics10000.csv") do |row|
    	unless count < 2
	      puts row.join
	    	client.puts(row.join + "\n")
	    	sleep(1)
	    end
    	count += 1
    end
   client.close
 end
end
