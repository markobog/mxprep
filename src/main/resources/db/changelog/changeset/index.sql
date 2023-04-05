CREATE INDEX IF NOT EXISTS p_id_price
    ON mxprep.price USING btree
    (id_price ASC NULLS LAST)
    TABLESPACE pg_default;

CREATE INDEX IF NOT EXISTS p_statistics
    ON mxprep.price USING btree
    (date ASC NULLS LAST, low ASC NULLS LAST, high ASC NULLS LAST)
    TABLESPACE pg_default;

CREATE INDEX IF NOT EXISTS p_normalized_desc
     ON mxprep.price USING btree
     (high DESC NULLS FIRST, low DESC NULLS FIRST)
     TABLESPACE pg_default;

CREATE INDEX IF NOT EXISTS p_date
    ON mxprep.price USING btree
    (date ASC NULLS LAST)
    TABLESPACE pg_default;
