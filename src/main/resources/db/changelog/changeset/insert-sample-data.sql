-- Tenants
INSERT INTO tenant (id, name, created_at)
VALUES
  (1, 'Company A', now()),
  (2, 'Company B', now())
ON CONFLICT (id) DO NOTHING;

-- Roles
INSERT INTO role (id, name)
VALUES
  (1, 'SUPER_ADMIN'),
  (2, 'EVENT_MANAGER'),
  (3, 'STAFF'),
  (4, 'ATTENDEE'),
  (5, 'VENDOR')
ON CONFLICT (id) DO NOTHING;

-- Events
INSERT INTO "event" (id, name, tenant_id, created_at)
VALUES
  (1, 'Tech Conference 2025', 1, now()),
  (2, 'Startup Meetup', 1, now()),
  (3, 'Healthcare Summit', 2, now()),
  (4, 'Music Festival', 2, now())
ON CONFLICT (id) DO NOTHING;

-- Users
INSERT INTO app_user (id, username, email, password_hash, tenant_id, created_at)
VALUES
  (1, 'admin', 'admin@super.com', 'password123', NULL , now()),
  (2, 'alice', 'alice@companya.com', 'password123', 1, now()),
  (3, 'bob', 'bob@companya.com', 'password123', 1, now()),
  (4, 'charlie', 'charlie@companyb.com', 'password123', 2, now()),
  (5, 'diana', 'diana@companyb.com', 'password123', 2, now()),
  (6, 'joe', 'joe@attendee.com', 'password123', 1, now())
ON CONFLICT (id) DO NOTHING;

-- User Roles
INSERT INTO user_role (user_id, role_id)
VALUES
  (1, 1), -- admin -> SUPER_ADMIN
  (2, 2), -- alice -> EVENT_MANAGER
  (3, 3), -- bob -> STAFF
  (4, 2), -- charlie -> EVENT_MANAGER
  (5, 3),  -- diana -> STAFF
  (6, 4)  -- joe -> ATTENDEE
ON CONFLICT (user_id, role_id) DO NOTHING;

-- Tickets
INSERT INTO ticket (id, attendee_id, event_id, seat_number, created_at)
VALUES
  (1, 2, 1, 'A1', now()),  -- bob -> Tech Conference
  (2, 4, 3, 'B1', now())   -- diana -> Healthcare Summit
ON CONFLICT (id) DO NOTHING;

-- Vendor Booths
INSERT INTO vendor_booth (id, booth_name, vendor_id, event_id, location, created_at)
VALUES
  (1, 'Booth 1', 1, 1, 'Hall A', now()) -- alice -> Tech Conference
ON CONFLICT (id) DO NOTHING;
