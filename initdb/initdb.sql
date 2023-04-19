CREATE TABLE "players" (
                           "id" INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                           "username" varchar UNIQUE,
                           "avatar" varchar,
                           "access_token" varchar UNIQUE
);

CREATE TABLE "rooms" (
                         "id" INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                         "name" varchar,
                         "capacity" int,
                         "state" varchar
);

CREATE TABLE "players_rooms" (
                                 "player_id" int,
                                 "room_id" int,
                                 PRIMARY KEY ("player_id", "room_id")
);

CREATE TABLE "rounds" (
                          "id" INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                          "room_id" int,
                          "prev_round_id" int,
                          "type" varchar,
                          "start_unix_date" int,
                          "timespan" int
);

CREATE TABLE "turns" (
                         "id" INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                         "round_id" int,
                         "player_id" int,
                         "prev_turn_id" int,
                         "attachment" varchar
);

ALTER TABLE "players_rooms" ADD FOREIGN KEY ("player_id") REFERENCES "players" ("id");

ALTER TABLE "players_rooms" ADD FOREIGN KEY ("room_id") REFERENCES "rooms" ("id");

ALTER TABLE "rounds" ADD FOREIGN KEY ("room_id") REFERENCES "rooms" ("id");

ALTER TABLE "turns" ADD FOREIGN KEY ("prev_turn_id") REFERENCES "turns" ("id");

ALTER TABLE "turns" ADD FOREIGN KEY ("round_id") REFERENCES "rounds" ("id");

ALTER TABLE "turns" ADD FOREIGN KEY ("player_id") REFERENCES "players" ("id");

ALTER TABLE "rounds" ADD FOREIGN KEY ("prev_round_id") REFERENCES "rounds" ("id");
