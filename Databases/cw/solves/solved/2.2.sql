
select TeamName from Teams where
  TeamId not in (
    select distinct TeamId from Sessions natural join Runs
    where ContestId = :ContestId and Letter = :Letter and Accepted = 1
  )
