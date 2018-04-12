

ALTER TABLE `client`
  ADD PRIMARY KEY (client_id),
  ADD UNIQUE KEY phone_no (phone_no);

ALTER TABLE credentials
  ADD PRIMARY KEY (credentials_id),
  ADD KEY user_id (user_id);

ALTER TABLE credit
  ADD PRIMARY KEY (id);

ALTER TABLE date_schedule
  ADD PRIMARY KEY (schedule_id),
  ADD UNIQUE KEY title (title);

ALTER TABLE date_schedule_groups
  ADD PRIMARY KEY (group_fk,schedule_fk);

ALTER TABLE date_sms_cost
  ADD PRIMARY KEY (cost_id),
  ADD KEY schedule (schedule);

ALTER TABLE groups
  ADD PRIMARY KEY (group_id),
  ADD UNIQUE KEY group_name (group_name);

ALTER TABLE group_clients
  ADD PRIMARY KEY (group_fk,client_fk);

ALTER TABLE month_schedule
  ADD PRIMARY KEY (schedule_id),
  ADD UNIQUE KEY title (title);

ALTER TABLE month_schedule_groups
  ADD PRIMARY KEY (group_fk,month_fk);

ALTER TABLE month_sms_cost
  ADD PRIMARY KEY (cost_id),
  ADD KEY schedule (schedule);

ALTER TABLE ondemand_groups
  ADD PRIMARY KEY (group_fk,sms_fk);

ALTER TABLE ondemand_sms
  ADD PRIMARY KEY (sms_id);

ALTER TABLE `user`
  ADD PRIMARY KEY (user_id),
  ADD UNIQUE KEY email (email);

ALTER TABLE user_sms
  ADD PRIMARY KEY (user_fk,sms_fk);

ALTER TABLE week_schedule
  ADD PRIMARY KEY (schedule_id),
  ADD UNIQUE KEY title (title);

ALTER TABLE week_schedule_groups
  ADD PRIMARY KEY (group_fk,week_fk);

ALTER TABLE week_sms_cost
  ADD PRIMARY KEY (cost_id),
  ADD KEY schedule (schedule);


ALTER TABLE `client`
  MODIFY client_id int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=78;
ALTER TABLE credit
  MODIFY id int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
ALTER TABLE date_schedule
  MODIFY schedule_id int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
ALTER TABLE date_sms_cost
  MODIFY cost_id int(11) NOT NULL AUTO_INCREMENT;
ALTER TABLE groups
  MODIFY group_id int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
ALTER TABLE month_schedule
  MODIFY schedule_id int(11) NOT NULL AUTO_INCREMENT;
ALTER TABLE month_sms_cost
  MODIFY cost_id int(11) NOT NULL AUTO_INCREMENT;
ALTER TABLE ondemand_sms
  MODIFY sms_id int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;
ALTER TABLE `user`
  MODIFY user_id int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=128;
ALTER TABLE week_schedule
  MODIFY schedule_id int(11) NOT NULL AUTO_INCREMENT;
ALTER TABLE week_sms_cost
  MODIFY cost_id int(11) NOT NULL AUTO_INCREMENT;
  
  
CREATE TABLE `client` (
  client_id int(11) NOT NULL,
  country_code varchar(255) NOT NULL,
  phone_no varchar(255) NOT NULL,
  full_name varchar(255) NOT NULL,
  telecom varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE credentials (
  credentials_id int(11) NOT NULL,
  active tinyint(1) NOT NULL,
  logged_in tinyint(1) NOT NULL,
  password varchar(255) NOT NULL,
  role varchar(255) NOT NULL,
  last_signin varchar(255) NOT NULL,
  user_id int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE credit (
  id int(11) NOT NULL,
  credit_amount double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE date_schedule (
  schedule_id int(11) NOT NULL,
  title varchar(255) NOT NULL,
  message varchar(255) NOT NULL,
  date varchar(255) NOT NULL,
  time varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE date_schedule_groups (
  group_fk int(11) NOT NULL,
  schedule_fk int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE date_sms_cost (
  cost_id int(11) NOT NULL,
  cost double NOT NULL,
  date_sent date NOT NULL,
  schedule int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE group_clients (
  group_fk int(11) NOT NULL,
  client_fk int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE groups (
  group_id int(11) NOT NULL,
  group_name varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE month_schedule (
  schedule_id int(11) NOT NULL,
  title varchar(255) NOT NULL,
  message varchar(255) NOT NULL,
  month_date int(11) NOT NULL,
  time varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE month_schedule_groups (
  group_fk int(11) NOT NULL,
  month_fk int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE month_sms_cost (
  cost_id int(11) NOT NULL,
  cost double NOT NULL,
  date_sent date NOT NULL,
  schedule int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE ondemand_groups (
  group_fk int(11) NOT NULL,
  sms_fk int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE ondemand_sms (
  sms_id int(11) NOT NULL,
  message varchar(255) NOT NULL,
  cost double NOT NULL,
  date date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `user` (
  user_id int(11) NOT NULL,
  first_name varchar(255) NOT NULL,
  last_name varchar(255) NOT NULL,
  email varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE user_sms (
  user_fk int(11) NOT NULL,
  sms_fk int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE week_schedule (
  schedule_id int(11) NOT NULL,
  title varchar(255) NOT NULL,
  message varchar(255) NOT NULL,
  date varchar(255) NOT NULL,
  time varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE week_schedule_groups (
  group_fk int(11) NOT NULL,
  week_fk int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE week_sms_cost (
  cost_id int(11) NOT NULL,
  cost double NOT NULL,
  date_sent date NOT NULL,
  schedule int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
