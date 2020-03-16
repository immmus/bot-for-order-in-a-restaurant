DROP TABLE IF EXISTS MENU;
DROP TABLE IF EXISTS menu_positions;
DROP SEQUENCE IF EXISTS menu_positions_seq;
DROP TYPE IF EXISTS CATEGORY_TYPE;

CREATE SEQUENCE menu_positions_seq START 100000;

CREATE TYPE CATEGORY_TYPE AS ENUM ('FOOD', 'BAR', 'OTHER');

CREATE TABLE menu_positions (
    id INTEGER PRIMARY KEY DEFAULT nextval('menu_positions_seq'),
    name TEXT NOT NULL,
    price NUMERIC NOT NULL,
    category CATEGORY_TYPE,
    description TEXT NOT NULL
);

CREATE TABLE MENU (
    position_id INTEGER NOT NULL REFERENCES menu_positions(id)
)