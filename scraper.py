from bs4 import BeautifulSoup
import requests
import uni2ascii

def get_resistance_multipliers(soup):
    resistances = soup.find("div", "weaknesses-resistances").findAll("li")
    result = []
    for resistance in resistances:
        text = uni2ascii.unicode_to_ascii(resistance.text)
        values = text.split()

        if(len(values) > 1):
            pair = (values[0], values[1])
        else:   
            pair = (values[0], 1)
        result.append(pair)
    return result
    
def get_element_types(soup):
    types = soup.find("ul", "details").findAll("span")
    result = []
    for type in types:
        result.append(uni2ascii.unicode_to_ascii(type.text))
    return result

base = "http://www.azurilland.com/pokedex/"
gen = "gen-1/"

for id in range(1,152):
   print(base+gen+str(id))

## test for pokemon page with id 1 -- bulbasaur
page = requests.get(base+gen+"/1")

soup = BeautifulSoup(page.text, "lxml")

#RESISTANCES
resistances = get_resistance_multipliers(soup)
print(resistances)

#ELEMENT TYPES
types = get_element_types(soup)
print(types)