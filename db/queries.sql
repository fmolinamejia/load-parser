select ip, count(*) as total 
from log_file 
where date between '2017-01-01 00:00:00' 
and '2017-01-01 23:59:59' 
group by ip having total >= 0  
order by total;

select ip 
from log_file 
where date between '2017-01-01 00:00:00' 
and '2017-01-01 23:59:59' 
group by ip having count(*) >= 0  
order by ip;

select * 
from log_file 
where ip = '192.168.145.211' 
order by date;