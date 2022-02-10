
insert into Sessions (Start, TeamId, ContestId)
  select current_timestamp, TeamId, :ContestId from (
    select s.TeamId from Sessions s where s.ContestId = :ContestId
  ) name
