CREATE FUNCTION add_log()
    RETURNS trigger
    LANGUAGE plpgsql
AS
$$
BEGIN
INSERT INTO logs(id, text, created) VALUES (DEFAULT, 'Added user with id = ' || NEW.id, NOW());
RETURN NEW;
END;
$$;

CREATE TRIGGER add_user_create_trg AFTER INSERT ON users FOR EACH ROW EXECUTE FUNCTION add_log();