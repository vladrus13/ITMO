
select distinct TeamId from
  Sessions natural join
  Runs
  where ContestId = :ContestId
  and Letter = :Letter
  and Accepted = 0
