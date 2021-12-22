INSERT INTO lectors (degree, first_name, last_name, salary) VALUES ('PROFESSOR', 'Roman', 'Romanov', 900);
INSERT INTO lectors (degree, first_name, last_name, salary) VALUES ('ASSISTANT', 'Ivan', 'Petrov', 954);

INSERT INTO departments (name) VALUES ('Department');

INSERT INTO departments_lectors (department_id, lector_id) VALUES (1, 1);
INSERT INTO departments_lectors (department_id, lector_id) VALUES (1, 2);

UPDATE departments SET head_lector_id = 1 WHERE id = 1;