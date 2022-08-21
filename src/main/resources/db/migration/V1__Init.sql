create table if not exists public.accounts (
    id bigserial primary key,
    created_at timestamp without time zone,
    amount varchar
);

create table if not exists public.transactions (
    id bigserial primary key,
    created_at timestamp without time zone,
    from_account_id int8,
    to_account_id int8,
    amount varchar
);

create index transactions__from_account_id on transactions (from_account_id);
create index transactions__to_account_id on transactions (to_account_id);