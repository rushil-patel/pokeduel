-- get pikachu
select * 
from 
   Pokemon P 
   JOIN PokemonType PT ON P.id = PT.pId 
   JOIN Resistance R ON R.pId = P.id 
   JOIN Type T ON T.id = R.typeId
   JOIN PokemonStats PS ON PS.pId = P.id
WHERE P.name = 'Pikachu'
;

-- updates overall for pokemon
