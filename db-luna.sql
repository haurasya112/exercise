PGDMP         	                 |            luna    15.2    15.2     "           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            #           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            $           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            %           1262    25976    luna    DATABASE     {   CREATE DATABASE luna WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_Indonesia.1252';
    DROP DATABASE luna;
                postgres    false            �            1259    27238    _user    TABLE     �   CREATE TABLE public._user (
    company_name character varying(255),
    email character varying(255),
    full_name character varying(255),
    password character varying(255),
    user_id character varying(36) NOT NULL
);
    DROP TABLE public._user;
       public         heap    postgres    false            �            1259    27245    product    TABLE     e  CREATE TABLE public.product (
    item_cost double precision NOT NULL,
    item_price double precision NOT NULL,
    stock double precision,
    category character varying(255),
    id character varying(255) NOT NULL,
    item_name character varying(255),
    sku character varying(255),
    tax_id character varying(255),
    uom character varying(255)
);
    DROP TABLE public.product;
       public         heap    postgres    false            �            1259    27252    tax    TABLE     �   CREATE TABLE public.tax (
    rate double precision,
    id character varying(255) NOT NULL,
    name character varying(255)
);
    DROP TABLE public.tax;
       public         heap    postgres    false            �            1259    27259    token    TABLE     4  CREATE TABLE public.token (
    expired boolean NOT NULL,
    id integer NOT NULL,
    revoked boolean NOT NULL,
    token character varying(255),
    token_type character varying(255),
    user_id character varying(36),
    CONSTRAINT token_token_type_check CHECK (((token_type)::text = 'BEARER'::text))
);
    DROP TABLE public.token;
       public         heap    postgres    false            �            1259    27237 	   token_seq    SEQUENCE     s   CREATE SEQUENCE public.token_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
     DROP SEQUENCE public.token_seq;
       public          postgres    false            �            1259    27269    transaction    TABLE     @  CREATE TABLE public.transaction (
    is_sale boolean,
    total double precision,
    total_before_tax double precision,
    invoice_date timestamp(6) without time zone,
    id character varying(255) NOT NULL,
    invoice_no character varying(255),
    note character varying(255),
    status character varying(255)
);
    DROP TABLE public.transaction;
       public         heap    postgres    false            �            1259    27276    transaction_item_line    TABLE     �   CREATE TABLE public.transaction_item_line (
    qty double precision,
    id character varying(255) NOT NULL,
    product_id character varying(255),
    transaction_id character varying(255)
);
 )   DROP TABLE public.transaction_item_line;
       public         heap    postgres    false                      0    27238    _user 
   TABLE DATA           R   COPY public._user (company_name, email, full_name, password, user_id) FROM stdin;
    public          postgres    false    215   �!                 0    27245    product 
   TABLE DATA           j   COPY public.product (item_cost, item_price, stock, category, id, item_name, sku, tax_id, uom) FROM stdin;
    public          postgres    false    216   B"                 0    27252    tax 
   TABLE DATA           -   COPY public.tax (rate, id, name) FROM stdin;
    public          postgres    false    217    #                 0    27259    token 
   TABLE DATA           Q   COPY public.token (expired, id, revoked, token, token_type, user_id) FROM stdin;
    public          postgres    false    218   �#                 0    27269    transaction 
   TABLE DATA           s   COPY public.transaction (is_sale, total, total_before_tax, invoice_date, id, invoice_no, note, status) FROM stdin;
    public          postgres    false    219   �$                 0    27276    transaction_item_line 
   TABLE DATA           T   COPY public.transaction_item_line (qty, id, product_id, transaction_id) FROM stdin;
    public          postgres    false    220   a%       &           0    0 	   token_seq    SEQUENCE SET     7   SELECT pg_catalog.setval('public.token_seq', 1, true);
          public          postgres    false    214            {           2606    27244    _user _user_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY public._user
    ADD CONSTRAINT _user_pkey PRIMARY KEY (user_id);
 :   ALTER TABLE ONLY public._user DROP CONSTRAINT _user_pkey;
       public            postgres    false    215            }           2606    27251    product product_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.product DROP CONSTRAINT product_pkey;
       public            postgres    false    216                       2606    27258    tax tax_pkey 
   CONSTRAINT     J   ALTER TABLE ONLY public.tax
    ADD CONSTRAINT tax_pkey PRIMARY KEY (id);
 6   ALTER TABLE ONLY public.tax DROP CONSTRAINT tax_pkey;
       public            postgres    false    217            �           2606    27266    token token_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.token
    ADD CONSTRAINT token_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.token DROP CONSTRAINT token_pkey;
       public            postgres    false    218            �           2606    27268    token token_token_key 
   CONSTRAINT     Q   ALTER TABLE ONLY public.token
    ADD CONSTRAINT token_token_key UNIQUE (token);
 ?   ALTER TABLE ONLY public.token DROP CONSTRAINT token_token_key;
       public            postgres    false    218            �           2606    27282 0   transaction_item_line transaction_item_line_pkey 
   CONSTRAINT     n   ALTER TABLE ONLY public.transaction_item_line
    ADD CONSTRAINT transaction_item_line_pkey PRIMARY KEY (id);
 Z   ALTER TABLE ONLY public.transaction_item_line DROP CONSTRAINT transaction_item_line_pkey;
       public            postgres    false    220            �           2606    27275    transaction transaction_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.transaction
    ADD CONSTRAINT transaction_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.transaction DROP CONSTRAINT transaction_pkey;
       public            postgres    false    219            �           2606    27288 1   transaction_item_line fkevuvsq3q0sepl2uqee65a98wh    FK CONSTRAINT     �   ALTER TABLE ONLY public.transaction_item_line
    ADD CONSTRAINT fkevuvsq3q0sepl2uqee65a98wh FOREIGN KEY (product_id) REFERENCES public.product(id);
 [   ALTER TABLE ONLY public.transaction_item_line DROP CONSTRAINT fkevuvsq3q0sepl2uqee65a98wh;
       public          postgres    false    216    220    3197            �           2606    27283 !   token fkiblu4cjwvyntq3ugo31klp1c6    FK CONSTRAINT     �   ALTER TABLE ONLY public.token
    ADD CONSTRAINT fkiblu4cjwvyntq3ugo31klp1c6 FOREIGN KEY (user_id) REFERENCES public._user(user_id);
 K   ALTER TABLE ONLY public.token DROP CONSTRAINT fkiblu4cjwvyntq3ugo31klp1c6;
       public          postgres    false    3195    218    215            �           2606    27293 1   transaction_item_line fkt8gd4iyhvt4mttw40bbia6se5    FK CONSTRAINT     �   ALTER TABLE ONLY public.transaction_item_line
    ADD CONSTRAINT fkt8gd4iyhvt4mttw40bbia6se5 FOREIGN KEY (transaction_id) REFERENCES public.transaction(id);
 [   ALTER TABLE ONLY public.transaction_item_line DROP CONSTRAINT fkt8gd4iyhvt4mttw40bbia6se5;
       public          postgres    false    219    220    3205               �   x�%���  �<���2��n�jcɵ���������w�6�1���J"L���33C���"�`�Q�U�4�j�����m��Q��j����:�w3��r�՝��LKb(Hʨ Ҵ�0ȁH&i��Ғ��]2����+�         �   x����JA��u�S�J������A�
�
R}�aƉ���!�2���s��3�s����2N�6t�*0� ���\*k�����㴼nu�ϯ�i>�/��Kz����Q�AK��U"���^{"���y����͢�]��Q�YTP��ju�֢e?�+�%�,��"J]����k�e*�I���2�9�E����Sn�<���35�@         �   x�̱u1 �Zڅ<@I�=���)� ��`{��	�x��i�Z[�}w�>xW�-Oơr?����C�\7�V� 6�\<h'����`Q��S�Co'a~AȰ�C�����*l��8��tB�M<��s���y�^�����Sk}h,�         �   x���
�0 ���/��lb��f�0��ps��B�����<�� ���r���D<�8�M��9�G��OYĴ̖y\�2�����Q_�ٷ�#XBb�P��'��spW�j�Y9�T�b�b ����r��ἥ���7����A�W��݈��HA�{�֘TN�=�)\k%����N�eY_�<|         �   x�}��m1߼*� �HId�H��$*���C�
8��~fg'�. �)3RZ�e�(�C�q��[B�R��:rvO��DI��9o�;�|<�n���p��N�{�|I��H{-#&J�Β�8lD�e�P!n����c�X)A����$�]���v,��Em��̠������[�d	�tCi��X�d����R��9����m�� ��a�         �   x��й�1P{&~I\�\���a�x�i|d�*�9���:���43�����\���|C�RЌ,FEK��I��cM`��I1����;N�V��2�	!��־�L��<��m����2�Z�[���L���V6�C�t?<�F|y��3`t�K�y?l�c�D+ڷS`A�[cCJ���7K,�o���6|������O�k#y�`}�~����s�     