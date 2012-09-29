require 'csv'
require 'json'

Encoding.default_external = Encoding::UTF_8
Encoding.default_internal = Encoding::UTF_8

CSV.foreach("SkobrevOlympics10000.csv") do |line|
	row = line.join.split(';')
	puts "{"
	puts "\t\"roundNumber\": \"#{row[0]}\","
	puts "\t\"distance\": \"#{row[1]}\","
	puts "\t\"lapTime\": \"#{row[2]}\","
	puts "\t\"totalTime\": \"#{row[3]}\""
	puts "},"
end