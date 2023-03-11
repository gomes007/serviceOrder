
# Dev tools
db-up:
	@docker compose up -d

db-down:
	@docker compose down -v

db-restart: db-down db-up
