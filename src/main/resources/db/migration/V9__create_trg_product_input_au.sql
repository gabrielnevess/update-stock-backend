CREATE OR REPLACE FUNCTION FUNC_PRODUCT_INPUT_AU()
 RETURNS trigger AS $$

 DECLARE COUNT_STOCK INTEGER;

BEGIN

    SELECT COUNT(*) INTO COUNT_STOCK FROM TB_STOCK WHERE ID_PRODUCT = NEW.ID_PRODUCT;

    IF COUNT_STOCK > 0 THEN
        UPDATE TB_STOCK SET
            QTD = QTD + (NEW.QTD - OLD.QTD),
            UPDATED_AT = TO_CHAR(now(), 'yyyy-MM-dd HH24:mm:ss')::timestamp
        WHERE ID_PRODUCT = NEW.ID_PRODUCT;

    END IF;

    RETURN NULL;
END;
$$
LANGUAGE 'plpgsql';

DROP TRIGGER IF EXISTS TRG_PRODUCT_INPUT_AU ON PUBLIC.TB_PRODUCT_INPUT;

CREATE TRIGGER TRG_PRODUCT_INPUT_AU
AFTER UPDATE ON TB_PRODUCT_INPUT
FOR EACH ROW EXECUTE PROCEDURE FUNC_PRODUCT_INPUT_AU();