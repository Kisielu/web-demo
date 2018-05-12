alter table github_data add CONSTRAINT UK_5hw1l8hyexajpvtusdgsu26l5
		unique (full_name);
alter table owner_data add CONSTRAINT UK_2w16cjbxaxset61r6hmqj1vgp
		unique (login);