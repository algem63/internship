package internship.constants;

public interface Queries {

    String CREATE_USERS_TABLE = """
            CREATE TABLE IF NOT EXISTS public.users(
                id integer NOT NULL GENERATED ALWAYS AS IDENTITY,
                name character varying(100) NOT NULL,
                email character varying(255),
                status character varying(10)  NOT NULL CHECK(status IN ('pending', 'active', 'inactive', 'blocked')),
                created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
                CONSTRAINT users_pkey PRIMARY KEY (id),
                CONSTRAINT users_email_key UNIQUE (email)
            );
            """;

    String CREATE_ORDERS_TABLE = """
            CREATE TABLE IF NOT EXISTS public.orders (
                id integer NOT NULL GENERATED ALWAYS AS IDENTITY,
                user_id integer NOT NULL,
                amount numeric(10,2) NOT NULL,
                status character varying(10) NOT NULL CHECK(status IN ('pending', 'processing', 'shipped', 'delivered', 'cancelled')),
                created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
                CONSTRAINT orders_pkey PRIMARY KEY (id),
                CONSTRAINT orders_user_id_fkey FOREIGN KEY (user_id)
                    REFERENCES public.users (id) MATCH SIMPLE
                    ON UPDATE NO ACTION
                    ON DELETE CASCADE,
                CONSTRAINT orders_amount_check CHECK (amount > 0::numeric)
            )""";

    String DROP_ORDERS_TABLE = """
            DROP TABLE IF EXISTS public.orders""";

    String INSERT_ORDER = """
            INSERT INTO public.orders (order_id, status)
            VALUES (?, ?)
            ON CONFLICT (order_id) DO NOTHING
            """;

    String COUNT_ORDERS = """
            SELECT COUNT(*) FROM public.orders WHERE order_id = ?
            """;

    String INSERT_TEST_USERS_QUERY = """
            INSERT INTO users (name, email, status, created_at) VALUES
                ('John Doe', 'john.doe@gmail.com', 'active', '2024-01-15 09:30:00'),
                ('Jane Smith', 'jane.smith@yahoo.com', 'active', '2024-02-20 14:45:22'),
                ('Maria Garcia', 'maria.garcia@outlook.com', 'active', '2024-03-10 11:20:15'),
                ('Sarah Brown', 'sarah.brown@company.co.uk', 'active', '2024-04-05 16:55:43'),
                ('Emily Davis', 'emily.davis@work.org', 'active', '2024-05-12 08:10:37'),
                ('Robert Wilson', 'robert.w@proton.me', 'active', '2024-06-18 22:30:11'),
                ('Robert Johnson', 'robert.j@mail.ru', 'inactive', '2023-09-01 10:00:00'),
                ('James Martinez', 'james.m@yandex.com', 'inactive', '2023-11-25 13:40:55'),
                ('David Lee', 'david.lee@hotmail.com', 'inactive', '2024-01-30 19:15:28'),
                ('David Wilson', 'david.w@icloud.com', 'active', NOW() - INTERVAL '2 days'),
                ('Linda Anderson', 'linda.a@seznam.cz', 'active', NOW() - INTERVAL '8 days'),
                ('Patricia Moore', 'pat.moore@email.com', 'pending', NOW() - INTERVAL '5 days'),
                ('Michael Taylor', 'michael.t@tutanota.com', 'blocked', '2023-12-05 06:45:33'),
                ('Thomas White', 'thomas.w@fastmail.com', 'blocked', '2024-03-15 09:55:20'),
                ('Barbara Miller', 'barbara.m@zoho.com', 'blocked', '2024-05-20 14:25:47'),
                ('Andrew Thompson', 'a.thompson@dev.team', 'active', '2024-06-01 10:00:00'),
                ('Michelle Rodriguez', 'm.rodriguez@api.test', 'active', '2024-06-15 13:30:00'),
                ('Kevin Lewis', 'kevin.lewis@backup.io', 'active', '2024-07-01 08:45:00'),
                ('Amanda Walker', 'amanda.w@stage.dev', 'active', '2024-07-10 16:20:00'),
                ('Mark Allen', 'mark.allen@prod.com', 'active', '2024-07-20 11:10:00')""";

    String INSERT_TEST_ORDERS = """
            INSERT INTO orders (user_id, amount, status, created_at) VALUES
               (1, 199.99, 'pending', '2024-01-20 10:15:00'),
               (1, 450.50, 'processing', '2024-01-25 14:30:22'),
               (1, 75.25, 'shipped', '2024-02-01 09:45:10'),
               (1, 320.00, 'delivered', '2024-02-10 16:20:45'),
               (1, 1200.00, 'cancelled', '2024-02-15 11:00:00'),
               (2, 89.90, 'pending', '2024-02-22 08:30:00'),
               (2, 210.45, 'processing', '2024-02-28 13:10:33'),
               (2, 567.80, 'shipped', '2024-03-05 17:55:20'),
               (2, 340.00, 'delivered', '2024-03-12 12:40:15'),
               (3, 1250.00, 'cancelled', '2024-03-15 09:00:00'),
               (3, 85.50, 'shipped', '2024-03-20 15:25:10'),
               (3, 230.75, 'delivered', '2024-03-25 11:15:45'),
               (4, 199.99, 'pending', '2024-04-10 10:00:00'),
               (4, 450.50, 'processing', '2024-04-15 14:20:30'),
               (4, 75.25, 'shipped', '2024-04-20 16:45:15'),
               (4, 320.00, 'delivered', '2024-04-25 09:30:00'),
               (4, 560.75, 'processing', '2024-05-01 13:50:20'),
               (4, 99.99, 'shipped', '2024-05-05 08:10:45'),
               (5, 340.00, 'delivered', '2024-05-15 12:00:00'),
               (5, 150.25, 'shipped', '2024-05-20 10:35:20'),
               (5, 75.50, 'pending', '2024-05-25 09:15:00'),
               (6, 99.99, 'pending', '2024-06-20 11:20:00'),
               (6, 45.50, 'processing', '2024-06-22 15:45:30'),
               (6, 780.00, 'shipped', '2024-06-25 08:00:15'),
               (6, 210.30, 'delivered', '2024-06-28 14:20:45'),
               (6, 320.00, 'cancelled', '2024-07-01 10:00:00'),
               (7, 450.00, 'pending', '2023-09-10 13:30:00'),
               (7, 180.50, 'cancelled', '2023-09-20 09:45:20'),
               (8, 230.25, 'delivered', '2023-12-01 08:30:00'),
               (8, 110.00, 'pending', '2023-12-10 14:15:10'),
               (8, 560.75, 'processing', '2023-12-20 11:40:45'),
               (8, 89.99, 'shipped', '2023-12-28 16:50:30'),
               (8, 450.00, 'delivered', '2024-01-05 09:25:15'),
               (8, 75.50, 'processing', '2024-01-10 13:10:00'),
               (9, 320.00, 'shipped', '2024-02-05 10:45:00'),
               (9, 150.75, 'delivered', '2024-02-10 08:20:30'),
               (10, 420.30, 'pending', '2026-08-31 16:00:00'),
               (10, 280.50, 'shipped', '2026-09-01 12:30:15'),
               (10, 95.99, 'processing', '2026-09-02 10:15:40'),
               (11, 150.00, 'pending', '2026-09-03 09:00:00'),
               (11, 89.25, 'shipped', '2026-09-04 14:50:10'),
               (12, 999.99, 'cancelled', '2026-09-01 11:30:00'),
               (13, 50.50, 'pending', '2023-12-10 08:00:00'),
               (13, 120.00, 'cancelled', '2023-12-15 13:20:15'),
               (14, 320.00, 'delivered', '2024-03-20 15:30:00'),
               (14, 180.50, 'shipped', '2024-03-25 11:15:20'),
               (14, 75.99, 'processing', '2024-03-28 09:45:45'),
               (14, 450.00, 'pending', '2024-04-01 10:00:00'),
               (15, 210.00, 'shipped', '2024-05-22 14:00:00'),
               (15, 95.50, 'delivered', '2024-05-25 16:30:20'),
               (15, 340.25, 'processing', '2024-05-28 12:10:45'),
               (16, 560.00, 'delivered', '2024-06-05 10:45:00'),
               (16, 230.50, 'shipped', '2024-06-10 08:20:30'),
               (17, 199.99, 'pending', '2024-06-18 14:30:00'),
               (17, 450.00, 'processing', '2024-06-22 10:15:00'),
               (18, 320.00, 'delivered', '2024-07-03 16:00:00'),
               (18, 150.50, 'shipped', '2024-07-05 09:30:00'),
               (19, 75.99, 'pending', '2024-07-12 11:20:00'),
               (19, 210.30, 'processing', '2024-07-15 13:45:00'),
               (20, 560.75, 'shipped', '2024-07-22 08:30:00'),
               (20, 89.99, 'delivered', '2024-07-25 15:10:00');
            """;

    String CLEAN_USERS_AND_ORDERS = """
            TRUNCATE TABLE public.users RESTART IDENTITY CASCADE""";

    String FIND_USER_BY_MAIL = """
                SELECT count(*)
                FROM public.users users
                WHERE users.email LIKE(?)""";

    String FIND_ACTIVE_USERS_FOR_THE_LAST_WEEK = """                
            SELECT count(*)
            FROM public.users users
            WHERE users.status = 'active'
            AND users.created_at >= NOW() - INTERVAL '7 days'""";

    String FIND_USERS_WITH_SPECIFIC_AMOUNT_OF_ORDERS = """
            SELECT count(*)
            FROM (
                SELECT users.id
                FROM public.users users
                JOIN public.orders ON users.id = orders.user_id
                GROUP BY users.id
                HAVING sum(orders.amount) > 1000
            )""";

    String INSERT_USER_AND_RETURN_ID = """
        INSERT INTO users(name, email, status)
        VALUES ('Pavel Melnikov', 'algem63@gmail.com', 'active')
        RETURNING id
        """;

    String INSERT_ORDER_FOR_USER = """
        INSERT INTO orders (user_id, amount, status)
        VALUES (?, 500, 'pending')
        """;

    String CHECK_IF_NEW_USER_EXISTS = """
        SELECT count(*)
        FROM public.users users
        WHERE users.name = 'Pavel Melnikov'
          AND users.email = 'algem63@gmail.com'
          AND users.status = 'active'
          AND EXISTS
            (SELECT 1
             FROM public.orders orders
             WHERE orders.user_id = users.id);""";

    String UPDATE_ORDER_STATUS = """
        UPDATE public.orders
        SET status = 'processing'
        WHERE user_id = (
            SELECT users.id
            FROM public.users AS users
            WHERE users.email = ?
        )""";

    String CHECK_IF_ORDER_STATUS_CHANGED = """
        SELECT 
            orders.status,
            count(orders)
        FROM users
        JOIN orders ON orders.user_id = users.id
        WHERE users.email = 'pat.moore@email.com'
        GROUP BY orders.status""";

    String INSERT_NEW_USER = """
        INSERT INTO users(name, email, status)
        VALUES (?, ?, ?)
        RETURNING id
        """;

    String FIND_CREATED_USER = """
        SELECT email
        FROM public.users
        WHERE id = ?
        """;

    String CREATE_ORDERS_TABLE_FOR_MQ_TEST = """
        CREATE TABLE IF NOT EXISTS public.orders (
            order_id integer NOT NULL,
            status character varying(10) NOT NULL,
            CONSTRAINT orders_pkey PRIMARY KEY (order_id)
        )""";
}
