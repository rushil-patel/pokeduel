import sys

with open("pokemonstats.csv", 'r') as stats:
   first_line = "INSERT INTO PokemonStats VALUES"
   data = stats.readlines()
   print first_line
   for line in data[:-1]:
      line = line.replace("\n", "")
      line = "(" + line + "),"
      print "   " + line
   line = data[-1]
   line = line.replace("\n", "")
   line = "(" + line + ");"
   print "   " + line