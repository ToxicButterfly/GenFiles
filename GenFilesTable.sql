CREATE TABLE IF NOT EXISTS public.lotlines
(
    idlines SERIAL PRIMARY KEY,
    date character varying(15) COLLATE pg_catalog."default",
    english character varying(15) COLLATE pg_catalog."default",
    russian character varying(15) COLLATE pg_catalog."default",
    integ character varying(15) COLLATE pg_catalog."default",
    doubl double precision
)