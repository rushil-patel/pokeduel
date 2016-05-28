import sys

poked = {}

poked['Normal'] = 1
poked['Fight'] = 2
poked['Flying'] = 3
poked['Poison'] = 4
poked['Ground'] = 5
poked['Rock'] = 6
poked['Bug'] = 7
poked['Ghost'] = 8
poked['Steel'] = 9
poked['Fire'] = 10
poked['Water'] = 11
poked['Grass'] = 12
poked['Electr'] = 13
poked['Psychc'] = 14
poked['Ice'] = 15
poked['Dragon'] = 16
poked['Dark'] = 17

with open("pokemonresistances.csv", 'r') as stats:
   first_line = "INSERT INTO Resistance VALUES"
   data = stats.readlines()
   print first_line
   for line in data[:-1]:
      line = line.replace("\n", "")
      line = ','.join([line.split(',')[0],str(poked[line.split(',')[1].strip()]),line.split(',')[2]])
      line = "(" + line + "),"
      print "   " + line
   line = data[-1]
   line = line.replace("\n", "")
   line = ','.join([line.split(',')[0],str(poked[line.split(',')[1].strip()]),line.split(',')[2]])
   line = "(" + line + ");"
   print "   " + line