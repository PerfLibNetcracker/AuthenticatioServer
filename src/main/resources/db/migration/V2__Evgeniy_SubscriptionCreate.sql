create table subscription
(
    sub_id   bigserial primary key,
    end_date timestamp
);

create table users_subscriptions
(
    user_id         bigint not null references users (id),
    subscription_id bigint not null references subscription (sub_id),
    primary key (user_id, subscription_id)
);