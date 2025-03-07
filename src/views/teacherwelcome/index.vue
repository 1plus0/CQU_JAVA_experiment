<template>
	<!-- 主体区域 -->
	<div class="section">
		<el-row gutter="10" style="flex-wrap: wrap; flex-direction: row">
			<!-- 个人信息 -->
			<el-col :span="7">
				<el-card class="box-card person">
					<div slot="header" class="clearfix">
						<span>个人信息</span>
						<el-button style="float: right; padding: 3px 0" type="text">...</el-button>
					</div>
					<img src="../../assets/u169.svg" />
					<ul>
						<li>姓名: {{teacher.name}}</li>
						<li>工号: {{ user.id }}</li>
						<li>学院: {{teacher.college}}</li>
					</ul>
				</el-card>
			</el-col>
			<!-- 快捷入口 -->
			<el-col :span="17">
				<el-card class="box-card quick">
					<div slot="header" class="clearfix">
						<span>快捷入口</span>
						<el-button style="float: right; padding: 3px 0" type="text">...</el-button>
					</div>
					<ul>
						<router-link to="/teacherscore">
							<li>
								<img src="../../assets/icon/地区统计-01.png" />
								<span class="text">成绩管理</span>
							</li>
						</router-link>
						
						<router-link to="/teacherdata">
							<li>
								<img src="../../assets/icon/报表统计.png" />
								<span class="text">数据统计</span>
							</li>
						</router-link>
						
	
					</ul>
				</el-card>
			</el-col><!-- 快捷菜单end -->
	
			<!-- 日历信息 -->
			<el-col :span="7" style="margin-top: 10px;">
				<div>
					<el-card class="box-card calendar">
						<div slot="header" class="clearfix">
							<span>今日日程</span>
							<el-button style="float: right; padding: 3px 0" type="text">...</el-button>
						</div>
						<el-calendar v-model="value"></el-calendar>
					</el-card>
				</div>
			</el-col>
			<!-- 通知公告 -->
			<el-col :span="17" style="margin-top: 10px;">
				<el-card class="box-card notice">
					<div slot="header" class="clearfix">
						<span>正在教学教学班</span>
					</div>
					<el-table :data="teachingclasses" style="width: 100%" size="small">
						<el-table-column type="index" label="序号" width="70" align="center" >
							<template slot-scope="scope">
							{{ scope.$index + 1 }}
							</template>
						</el-table-column>
						<el-table-column prop="id" label="教学班号" sortable>
						</el-table-column>
						<el-table-column prop="course" label="课程名称" >
						</el-table-column>
						<el-table-column prop="sestem" label="开课学期" >
						</el-table-column>
					</el-table>
				</el-card>
			</el-col>
		</el-row>
	</div>
</template>

<script>
	import axios from "axios";
	export default{
		data(){
			return{
				user: null,
				teachingclasses: [],
				courses:[],
				teacher:[],
			}
		},
		created() {
			// 从本地存储中读取用户信息
			const user = localStorage.getItem('user');
			if (user) {
				this.user = JSON.parse(user);
			}
			this.fetchCourses();
			this.findteacher();
		},
		methods:{
			async fetchCourses() {
				try {
				const params = {
					teacherId: this.user.id
					};
				const response = await axios.get('/selectTcByteacher',{params}); // 假设你使用的是 axios
				if (response.data.code === 200) {
					this.teachingclasses = response.data.data;
				} else {
					this.$message.error('加载教学班失败');
				}
				} catch (error) {
				console.error('获取教学班列表失败:', error);
				this.$message.error('获取教学班列表失败');
				}
			},
			
			async findteacher(){
				
				const namesResponse = await axios.get(`/selectTeacherById?id=${this.user.id}`);
				if (namesResponse.data.code === 200) {
					const teacher = namesResponse.data.data;
					this.teacher=teacher;
				} else {
					this.$message.error('加载教师失败');
				}
			},
			
		}
	}
</script>

<style scoped>
	/* 主体部分 */
	.section {
		height: 96%;
		width: 100%;
		display: flex;
	}
	
	.el-row {
		padding: 10px;
		width: 100%;
	
	}
	
	.box-card {
		width: 100%;
		position: relative;
		height: 260px;
	}
	
	.el-card__header {
		padding: 10px 10px;
		font-size: 14px;
	}
	
	.person img {
		width: 130px;
		margin-top: 15px;
	}
	
	.person ul {
		position: absolute;
		top: 100px;
		left: 50%;
	}
	
	.person ul li {
		padding: 0;
		line-height: 30px;
		list-style: none;
		font-size: 13px;
		color: #2f2f2f;
	}
	
	/* 快捷入口样式 */
	.quick ul {
		margin: -25px;
	
	}
	
	.quick ul li {
		list-style: none;
		margin-left: 28px;
		margin-top: 25px;
		width: 70px;
		height: 70px;
		position: relative;
		cursor: pointer;
		float: left;
	}
	
	.quick ul li img {
		width: 100%;
		height: 70px;
	}
	
	.quick .text {
		position: relative;
		margin: 20px 10px;
		font-size: 12px;
		bottom: 5px;
	}
	
	/* 日历样式 */
	.calendar {
		width: 100%;
		height: 450px;
	
	}
	
	/* 日期样式 */
	/deep/.el-calendar-day {
		height: 40px !important;
		width: 40px !important;
		border: none;
		text-align: center;
		line-height: 20px;
		align-items: center;
		padding: 0;
		font-size: 12px;
	}
	
	/* 日期标题 */
	/deep/.el-calendar__title {
		color: #626262;
		font-size: 12px;
	}
	
	/* 星期标题 */
	/deep/.el-calendar-table th {
		font-size: 13px;
	}
	
	.el-calendar__body {
		padding: 0px;
	}
	
	/* 通知公告样式 */
	.notice{
		height: 100%;
	}
	.notice ul li {
		list-style: none;
		height: 40px;
		line-height: 40px;
		border-bottom: 1px dashed gray;
		font-size: 13px;
		background: url("../../assets/05.png") no-repeat center left;
		background-size: 15px 15px;
		margin-left: 10px;
		cursor: pointer;
		position: relative;
	}
	
	.notice ul li span:first-child {
		display: inline-block;
		margin-left: 20px;
	}
	
	.notice ul li span:last-child {
		display: inline-block;
		position: absolute;
		right: 10px;
	}
	
	.notice ul li:hover {
		color: #00aaff;
	}
</style>