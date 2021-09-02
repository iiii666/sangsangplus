# Precedure Find_Max_Min_Avg_Var_Std
# Programmed by Seung_Hyun Nam
# Last update: 2021-09-02
import math
Num_data = 10
DataArray = []
count_data=0
max_value = -2147483648
min_value = 2147483648
sum_value = 0
avg_value=0
print("Input {} integer data".format(Num_data))
for i in range(0,Num_data):
    data = int(input("data: "))
    count_data += 1
    DataArray.append(data)
    if data>max_value:
        max_value = data
    if data<min_value:
        min_value = data
    sum_value = sum_value +data
avg_value = sum_value / Num_data

dev_sum = 0
for i in range(0,Num_data):
    dev = avg_value-DataArray[i]
    dev_sum += (dev*dev)
var = dev_sum / Num_data
std = math.sqrt(var)
print("Input data = ",DataArray)
print("Max= {}, Min = {}, Avg = {}, Var = {}, Std = {}".format(max_value,min_value,avg_value,var,std))