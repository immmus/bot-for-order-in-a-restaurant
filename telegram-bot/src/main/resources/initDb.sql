DROP TABLE IF EXISTS MENU;
DROP TABLE IF EXISTS menu_positions;
DROP SEQUENCE IF EXISTS menu_positions_seq;

CREATE SEQUENCE menu_positions_seq START 100000;

CREATE TABLE menu_positions
(
    id          INTEGER PRIMARY KEY DEFAULT nextval('menu_positions_seq'),
    name        TEXT    NOT NULL,
    price       NUMERIC NOT NULL,
    category    TEXT    NOT NULL,
    composition TEXT,
    description TEXT    NOT NULL,
    isActive    BOOL    NOT NULL    DEFAULT TRUE
);