select cast(sum(Mark) as double) / count(*) as AvgMark from Marks where StudentId = :StudentId
