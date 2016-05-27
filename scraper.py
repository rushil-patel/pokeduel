from bs4 import BeautifulSoup
import requests

base = "http://www.azurilland.com/pokedex/"
gen = "gen-1/"

for id in range(1,152):
   print(base+gen+str(id))

