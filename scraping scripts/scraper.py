from bs4 import BeautifulSoup
import requests
import uni2ascii
from PIL import Image
from StringIO import StringIO

base = "http://www.azurilland.com/pokedex/"
gen = "gen-1/"

def convert_to_float(frac_str):
    try:
        return float(frac_str)
    except ValueError:
        num, denom = frac_str.split('/')
        try:
            leading, num = num.split(' ')
            whole = float(leading)
        except ValueError:
            whole = 0
        frac = float(num) / float(denom)
        return whole - frac if whole < 0 else whole + frac

def get_resistance_multipliers(soup):
    resistances = soup.find("div", "weaknesses-resistances").findAll("li")
    result = []
    for resistance in resistances:
        text = uni2ascii.unicode_to_ascii(resistance.text)
        values = text.split()

        if(len(values) > 1):
            if(values[1] == 'immune'):
                values[1] = 0
            pair = (values[0], convert_to_float(values[1]))
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


def get_stats(soup):
    stats = soup.find("table", "stat-table").find("tbody").findAll("tr")
    result = []
    for stat in stats:
        result.append(uni2ascii.unicode_to_ascii(stat.findAll("td")[1].text))
    return result

def get_pokemon_data(soup):
    name = uni2ascii.unicode_to_ascii(soup.find("div","overview").find("h2","header").text)
    imgurl = soup.find("div", "profile-image").find("img")['src']
    imgdata = requests.get(imgurl)
    img = Image.open(StringIO(imgdata.content))
    return (name, img)

# writes pokemon info csv and writes pokemon sprites to file
def write_pokemon_data():
    with open("pokemoninfo.csv", "w") as f:

        for id in range(1,152):
            page = requests.get(base+gen+"/"+str(id))
            soup = BeautifulSoup(page.text, "lxml")
        
            data = get_pokemon_data(soup);
            f.write(str(id)+", "+"'"+data[0]+"'"+", "  +"'"+data[0]+".png"+"'"+"\n")
            print(str(id)+", "+"'"+data[0]+"'"+", "  +"'"+data[0]+".png"+"'"+"\n")
            #img = data[1]
            #img.save("sprites/"+data[0]+".png","PNG")



def write_stats():
    with open("pokemonstats.csv", "w") as f:
        
        for id in range(1,152):
            page = requests.get(base+gen+"/"+str(id))
            soup = BeautifulSoup(page.text, "lxml")
            data = []
                
            stats = get_stats(soup)
            stats.append("NULL")
            data.append(id)
            data.extend(stats)
            data = ', '.join(str(x) for x in data) + "\n"
            print(data)
            f.write(data)


## CAUTION: scrapes all immune values for certain pokemon
def write_resistances():
    with open("pokemonresistances.csv", "w") as f:
        for id in range(1, 152):
            page = requests.get(base+gen+"/"+str(id))
            soup = BeautifulSoup(page.text, "lxml")
            data = []

            resistances = get_resistance_multipliers(soup)

            for pair in resistances:
                data.append(str(id)+", "+ str(pair[0])+", "+str(pair[1])+"\n")
            data = ''.join(str(x) for x in data)
            f.write(data)

def write_types():
    with open("pokemonetypes.csv", "w") as f:
        for id in range(1,152):
            page = requests.get(base+gen+"/"+str(id))
            soup = BeautifulSoup(page.text, "lxml")
            data = []

            types = get_element_types(soup);
            for type in types:
                data.append(str(id)+", "+type+"\n")
            data = ''.join(str(x) for x in data)
            print(data)
            f.write(data)


write_pokemon_data()
