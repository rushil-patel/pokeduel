CREATE TABLE PokemonStats(
   pId INT,
   hp INT,
   att INT,
   def INT,
   sAtt INT,
   sDef INT,
   speed INT,
   overall INT,
   CONSTRAINT S_PK PRIMARY KEY(pId)
);

CREATE TABLE Pokemon(
   id INT,
   name VARCHAR(32),
   sprite VARCHAR(64),
   CONSTRAINT P_PK PRIMARY KEY(id)
);

CREATE TABLE Type(
   id INT,
   name VARCHAR(32),
   CONSTRAINT T_PK PRIMARY KEY(id),
   CONSTRAINT T_UNIQUE UNIQUE(name)
);

CREATE TABLE Resistance(
   pId INT,
   typeId INT,
   mult DECIMAL(4,2),
   CONSTRAINT R_FK_S FOREIGN KEY(pId) REFERENCES PokemonStats(pId),
   CONSTRAINT R_FK_T FOREIGN KEY(typeId) REFERENCES Type(id)
);

CREATE TABLE PokemonType(
   pId INT,
   typeId INT,
   CONSTRAINT PT_FK_P FOREIGN KEY(pId) REFERENCES Pokemon(id),
   CONSTRAINT PT_FK_T FOREIGN KEY(typeId) REFERENCES Type(id)
);

CREATE TABLE PokemonCost(
   pId INT,
   cost INT,
   CONSTRAINT P_PK_P PRIMARY KEY(pId)
);

CREATE TABLE Users(
   id INT,
   username VARCHAR(64),
   win INT,
   loss INT,
   CONSTRAINT U_PK PRIMARY KEY(id),
   CONSTRAINT U_UNIQUE UNIQUE(username)
);

CREATE TABLE MatchHistory(
   userOneId INT,
   userTwoId INT,
   winner INT,
   loser INT,
   id INT AUTO_INCREMENT,
   CONSTRAINT M_FK_1 FOREIGN KEY(userOneid) REFERENCES Users(id),
   CONSTRAINT M_FK_2 FOREIGN KEY(userTwoId) REFERENCES Users(id),
   CONSTRAINT M_UNIQUE UNIQUE(id)
);
