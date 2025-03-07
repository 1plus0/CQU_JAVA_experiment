<template>
	<div id="box" style="width: 1240px;height: 700px;background-color: #fff;margin: 10px;">
		
	</div>
</template>


<script>
	import axios from "axios";
	//import { markRaw } from 'vue';
	
	export default {
		data() {
			return {
				scores:[],
				coursesMap:[],
				courses:[],
				fetchedData:[],
			}

		},
		
		mounted() {
			this.getallScore();
			
			
			
			
		},
		methods: {
			async tongji(){
				this.fetchedData = {};
				// 遍历 scores 数组
					this.scores.forEach(scoreRecord => {
						
						// 找到对应的课程名称
						const course = this.coursesMap[scoreRecord.teachingclassId];
						
						if (!this.fetchedData[course]) {
								this.fetchedData[course] = {
									"0-60分": 0,
									"60-70分": 0,
									"70-80分": 0,
									"80-90分": 0,
									"90-100分": 0
								};
							}

				
						
				
						// 根据 totalscore 分类
						if (scoreRecord.totalscore >= 0 && scoreRecord.totalscore < 60) {
							this.fetchedData[course]["0-60分"]++;
						} else if (scoreRecord.totalscore >= 60 && scoreRecord.totalscore < 70) {
							this.fetchedData[course]["60-70分"]++;
						} else if (scoreRecord.totalscore >= 70 && scoreRecord.totalscore < 80) {
							this.fetchedData[course]["70-80分"]++;
						} else if (scoreRecord.totalscore >= 80 && scoreRecord.totalscore < 90) {
							this.fetchedData[course]["80-90分"]++;
						} else if (scoreRecord.totalscore >= 90 && scoreRecord.totalscore <= 100) {
							this.fetchedData[course]["90-100分"]++;
						}
					});
				
					
			},
			
			async createEchart() {
				
				var chartDom = document.getElementById('box');
				var myChart = this.$echarts.init(chartDom);
				var option;
				console.log(this.fetchedData);
				//const courses = Object.keys(scoreData);
				const scoreRanges = ["0-60分", "60-70分", "70-80分", "80-90分", "90-100分"];
				option = {
					tooltip: {
						trigger: 'axis',
						axisPointer: {
						// Use axis to trigger tooltip
						type: 'shadow' // 'shadow' as default; can also be 'line' or 'shadow'
					}
					},
				legend: {data: scoreRanges},
				grid: {
					left: '3%',
					right: '4%',
					bottom: '3%',
					containLabel: true
				},
				xAxis: {
						type: 'value',
						name: '人数'
					},
					yAxis: {
						type: 'category',
						data: this.courses.map(course => course.name),
					},
					series: scoreRanges.map(range => ({
								name: range,
								type: 'bar',
								stack: 'total',
								label: {
									show: true,
									color: '#FFFFFF'
								},
								itemStyle: {
									color: this.getColor(range) // 根据分数段设置颜色
								},
								emphasis: {
									focus: 'series'
								},
							data: this.courses.map(course => {
									const value = this.fetchedData[course.name]?this.fetchedData[course.name][range]:0;
									console.log('Score Data:', this.fetchedData[course.name]);
									console.log(`Data for ${course.name} in ${range}:`, value); // 调试信息
									return value;
								})
							//data: this.courses.map(course => (this.fetchedData[course]||{})[range] || 0)
							}))
						};
					
						myChart.setOption(option);
					
						// 为每个分数段设置颜色
						
				},	
				
				getColor (range){
					const colors = {
						'0-60分': '#ee6666',
						'60-70分': '#ffa435',
						'70-80分': '#f5f57a',
						'80-90分': '#a9df96',
						'90-100分': '#6284e8'
					};
					return colors[range];
				},
					/*series: [
					{
						name: '0-60分',
						type: 'bar',
						stack: 'total',
						label: {
						show: true,
						color: '#FFFFFF'
						},
					itemStyle: {
						color: '#ee6666' 
						},
					emphasis: {
						focus: 'series'
						},
					data: [320, 302, 301, 334, 390, 330, 320]
					},
					{
						name: '60-70分',
						type: 'bar',
						stack: 'total',
						label: {
							show: true,
							color: '#FFFFFF'
						},
						itemStyle: {
							color: '#ffa435'
						},
						emphasis: {
							focus: 'series'
						},
						data: [120, 132, 101, 134, 90, 230, 210]
						},
					{
						name: '70-80分',
						type: 'bar',
						stack: 'total',
						label: {
						show: true,
						color: '#c2c2c2'
						},
						itemStyle: {
							color: '#f5f57a'
						},
						emphasis: {
							focus: 'series'
						},
						data: [220, 182, 191, 234, 290, 330, 310]
						},
					{
						name: '80-90分',
						type: 'bar',
						stack: 'total',
						label: {
						show: true,
						color: '#FFFFFF'
						},
						itemStyle: {
						color: '#a9df96'
						},
						emphasis: {
							focus: 'series'
						},
						data: [150, 212, 201, 154, 190, 330, 410]
					},
					{
						name: '90-100分',
						type: 'bar',
						stack: 'total',
						label: {
							show: true,
							color: '#FFFFFF'
						},
						itemStyle: {
						color: '#6284e8'
						},
						emphasis: {
						focus: 'series'
						},
						data: [820, 832, 901, 934, 1290, 1330, 1320]
						}
					]
				};*/
				
				
            
			
			
			async getallScore() {
				
				try {
				const params = {
					studentId:  null, // 如果 studentId 为空字符串或未定义，则发送 null
					teachingclassId: '',
					};
				const response = await axios.get('/selectScoresByIds', { params });
				if (response.data.code === 200) {
					this.scores = response.data.data;
				} else {
					this.$message.error('未搜索到成绩');
				}
				} catch (error) {
				console.error('获取成绩失败:', error);
				this.$message.error('获取成绩失败');
				}
			this.fetchCoursesMap();
				},
				
			async fetchCoursesMap() {
				try {
					const response = await axios.get('/selectCourseByTc');
					if (response.data.code === 200) {
					this.coursesMap = response.data.data;
					} else {
					this.$message.error('课程信息获取失败');
					}
				} catch (error) {
					console.error('获取课程信息失败:', error);
					this.$message.error('获取课程信息失败');
				}
				this.fetchCourses();
			},
			
			async fetchCourses() {
				try {
				const response = await axios.get('/selectCourse'); // 假设你使用的是 axios
				if (response.data.code === 200) {
					this.courses = response.data.data;
				} else {
					this.$message.error('加载课程失败');
				}
				} catch (error) {
				console.error('获取课程列表失败:', error);
				this.$message.error('获取课程列表失败');
				}
				
				this.tongji();
				this.createEchart();
			},
		}
	}
</script>

<style>
</style>
  

