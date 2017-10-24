DO $$DECLARE
  system_id         UUID = '00000000-0000-0000-0000-000000000000';
BEGIN

  INSERT INTO oauth_clients (client_id, client_secret, client_resources) VALUES
    ('swagger-ui', 'web', 'authresource,webresource'),
    ('web', 'web', 'authresource,webresource'),
    ('mobile', 'mobile', 'authresource,mobileresource');

  --   -- Password: 12345
  INSERT INTO users (id, email, password, user_name, user_surname, user_status) VALUES
    (system_id,
     'admin@kramar.com',
     '$2a$10$oJD.FohR7Jhn.CHVh83KGOpsp.JNKuBjryHioPuR33F0U0yQ9Kqt2',
     'Admin user', 'Admin user', 'ACTIVE');

  INSERT INTO user2roles (user_id, role) VALUES (system_id, 'SUPER_ADMIN');

  INSERT INTO adverts (id, advert_type, advert_status, headline, price, currency_type, description, user_id, created_time, created_by, updated_time, updated_by, version) VALUES ('c7559c52-39c4-4551-afeb-4a12802808a0', 'SALE', 'ACTIVE', 'First Advert2', 99, 'USD', 'first ad in app', '00000000-0000-0000-0000-000000000000', '2017-09-19 18:20:13.353000', null, '2017-09-19 18:21:14.358000', null, 1);

END$$;
