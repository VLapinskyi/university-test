DROP TABLE IF EXISTS
    departments,
    departments_lectors,
    lectors
    CASCADE;
DROP TYPE IF EXISTS
    degree CASCADE;
    
CREATE TYPE degree AS ENUM ('ASSISTANT', 'ASSOCIATE_PROFESSOR', 'PROFESSOR');

CREATE TABLE lectors (
    id serial NOT NULL,
    degree degree NOT NULL,
    first_name character varying NOT NULL,
    last_name character varying NOT NULL,
    salary integer,
    PRIMARY KEY (id)
);

CREATE TABLE departments (
    id serial NOT NULL,
    name character varying NOT NULL,
    head_lector_id integer,
    PRIMARY KEY (id),
    FOREIGN KEY (head_lector_id) REFERENCES public.lectors(id),
    UNIQUE (name)
);

CREATE TABLE departments_lectors (
    department_id integer,
    lector_id integer,
    PRIMARY KEY (department_id, lector_id),
    FOREIGN KEY (department_id) REFERENCES public.departments(id),
    FOREIGN KEY (lector_id) REFERENCES public.lectors(id)
);