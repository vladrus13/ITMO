select TeamName
from 
Teams natural join
(select distinct TeamId from
  (select TeamId, ContestId from Contests, Teams
  except
  select TeamId, ContestId from
    Sessions natural join
    Runs) NotSolvedTeam) SolvedTeam
