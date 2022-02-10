select TeamName from 
  Teams natural join
  (select TeamId from 
    Teams 
    except 
    select TeamId from
      Teams natural join 
      Sessions natural join Runs
    where ContestId = :ContestId
  ) CompleteTeams
