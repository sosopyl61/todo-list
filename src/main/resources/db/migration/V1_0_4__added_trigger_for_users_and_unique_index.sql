create trigger add_user_create_trg
    after insert
    on public.users
    for each row
execute procedure public.add_log();

create unique index user_id_uniq
    on public.security (user_id);