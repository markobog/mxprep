CREATE SCHEMA IF NOT EXISTS mxprep;

CREATE TABLE IF NOT EXISTS mxprep.company
(
    id_company serial NOT NULL,
    company_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT company_pkey PRIMARY KEY (id_company)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS mxprep.company
    OWNER to postgres;

    CREATE TABLE IF NOT EXISTS mxprep.price
    (
        id_price serial NOT NULL,
        close numeric(38,2),
        date date,
        high numeric(38,2),
        low numeric(38,2),
        open numeric(38,2),
        id_company integer NOT NULL,
        volume character varying(255) COLLATE pg_catalog."default",
        CONSTRAINT price_pkey PRIMARY KEY (id_price),
        CONSTRAINT fkisimbqb0ru94ai70x2rvcsfpm FOREIGN KEY (id_company)
            REFERENCES mxprep.company (id_company) MATCH SIMPLE
            ON UPDATE NO ACTION
            ON DELETE NO ACTION
    )

    TABLESPACE pg_default;

    ALTER TABLE IF EXISTS mxprep.price
        OWNER to postgres;