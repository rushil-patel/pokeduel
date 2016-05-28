import sys

with open("pokemon.csv", 'r') as stats:
   first_line = "INSERT INTO Pokemon VALUES"
   data = stats.readlines()
   print first_line
   for line in data[:-1]:
      line = line.replace("\n", "")
      line = "(" + line + "),"
      print "   " + line
   line = data[-1]
   line.replace("\n", "")
   line = "(" + line + ");"
   print line