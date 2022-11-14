
CREATE TABLE EXCHANGE_QUOTES (
  ID SERIAL PRIMARY KEY ,
  CURRENCY_PAIR  varchar(250) NOT NULL,
  QUOTE numeric(15,6)
 );


INSERT INTO EXCHANGE_QUOTES (CURRENCY_PAIR, QUOTE)
 VALUES ('EUR_CHF',0.9758),
 ('EUR_USD',1.0318),
 ('EUR_GBP',0.8763);