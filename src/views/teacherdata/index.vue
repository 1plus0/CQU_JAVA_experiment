<template>
	<div id="box" style="width: 1240px;height: 700px;background-color: #fff;margin: 10px;">
		
	</div>
</template>

<script>
	import axios from "axios";
	export default {
		data() {
			return {
				teachingClasses:[],
				user: null,
				scores:[],
				scoreStats:[],
			}

		},
		mounted() {
			const user = localStorage.getItem('user');
			if (user) {
				this.user = JSON.parse(user);
			}
			
			
			this.get();
		},
		methods: {
			async fetchTeachingclasses(){
				try {
				const params = {
					teacherId: this.user.id
					};
				const response = await axios.get('/selectTcByteacher',{params}); // 假设你使用的是 axios
				if (response.data.code === 200) {
					this.teachingClasses = response.data.data;
				} else {
					this.$message.error('加载教学班失败');
				}
				} catch (error) {
				console.error('获取教学班列表失败:', error);
				this.$message.error('获取教学班列表失败');
				}
			},
			
			async tongji(){
				this.scoreStats={};
					this.teachingClasses.forEach(tc=>{
						if (!this.scoreStats[tc.id]) {
								this.scoreStats[tc.id] = {
									"0-60分": 0,
									"60-70分": 0,
									"70-80分": 0,
									"80-90分": 0,
									"90-100分": 0
								};
							}
						this.scores.forEach(score=>{
							if(score.teachingclassId==tc.id){
								if (score.totalscore >= 0 && score.totalscore < 60) {
									this.scoreStats[tc.id]["0-60分"]++;
								} else if (score.totalscore >= 60 && score.totalscore < 70) {
									this.scoreStats[tc.id]["60-70分"]++;
								} else if (score.totalscore >= 70 && score.totalscore < 80) {
									this.scoreStats[tc.id]["70-80分"]++;
								} else if (score.totalscore >= 80 && score.totalscore < 90) {
									this.scoreStats[tc.id]["80-90分"]++;
								} else if (score.totalscore >= 90 && score.totalscore <= 100) {
									this.scoreStats[tc.id]["90-100分"]++;
								}
							}
						})
					})
					
				
				
			},
			
			async get() {
				await this.fetchTeachingclasses();
					
					try {
					const params = {
						studentId: null, 
						teachingclassId: "",
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
			
					await this.tongji();
					
				
				this.createEchart();
					
				},
			
			
			async createEchart() {
				
				var chartDom = document.getElementById('box');
				var myChart = this.$echarts.init(chartDom);
				var option;
				const cc=this.scoreStats;
				console.log(cc);
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
						data: this.teachingClasses.map(tc => tc.id),
					},
					series: scoreRanges.map((range) => ({
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
							data: this.teachingClasses.map((tc) => {
								//const idStr = String(tc.id);
									console.log(cc[tc.id]);
									console.log(cc);
									console.log(tc.id);
									const value = cc[tc.id][range];
									
									
									//console.log('Score Data:', this.scoreStats['TC09']); 
									return value;
								})
							
							}))
						};
					
						myChart.setOption(option);
						
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
	},
	}
</script>

<style>
</style>
