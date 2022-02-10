select t.TeamName from 
  Teams t natural join 
  (select distinct s.TeamId from Sessions s where s.ContestId = :ContestId) Teams
