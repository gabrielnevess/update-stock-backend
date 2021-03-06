CREATE OR REPLACE FUNCTION FUNC_PRODUCT_OUTPUT_AI()
 RETURNS trigger AS $$

 DECLARE COUNT_STOCK INTEGER;

BEGIN

    SELECT COUNT(*) INTO COUNT_STOCK FROM TB_STOCK WHERE PRODUCT_ID = NEW.PRODUCT_ID;

    IF COUNT_STOCK > 0 THEN
        UPDATE TB_STOCK SET
            QTD = QTD + (NEW.QTD * -1),
            UPDATED_AT = TO_CHAR(now(), 'yyyy-MM-dd HH24:mm:ss')::timestamp
        WHERE PRODUCT_ID = NEW.PRODUCT_ID;

    END IF;

    RETURN NULL;
END;
$$
LANGUAGE 'plpgsql';

DROP TRIGGER IF EXISTS TRG_PRODUCT_OUTPUT_AI ON PUBLIC.TB_PRODUCT_OUTPUT;

CREATE TRIGGER TRG_PRODUCT_OUTPUT_AI
AFTER INSERT ON TB_PRODUCT_OUTPUT
FOR EACH ROW EXECUTE PROCEDURE FUNC_PRODUCT_OUTPUT_AI();