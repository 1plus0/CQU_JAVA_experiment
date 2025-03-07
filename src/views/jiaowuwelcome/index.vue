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
						<li>管理员账户</li>
					</ul>
				</el-card>
			</el-col>
			<!-- 快捷入口 -->
			<el-col :span="17">
				<el-card class="box-card quick">
					<div slot="header" class="clearfix">
						<span>快捷入口</span>
						<el-button style="float: right; padding: 5px 0" type="text">...</el-button>
					</div>
					<ul>
						<router-link to="/jiaowuscore">
							<li>
								<img src="../../assets/icon/地区统计-01.png" />
								<span class="text">成绩查询</span>
							</li>
						</router-link>
						
						<router-link to="/jiaowudata">
							<li>
								<img src="../../assets/icon/数据统计.png" />
								<span class="text">数据统计</span>
							</li>
						</router-link>
						<router-link to="/jiaowumanager">
							<li>
								<img src="../../assets/icon/团队管理.png" />
								<span class="text">班级管理</span>
							</li>
						</router-link>
						
						
						<router-link to="/jiaowucourse">
							<li>
								<img src="../../assets/icon/作品管理.png" />
								<span class="text">课程管理</span>
							</li>
						</router-link>
						
	
					</ul>
				</el-card>
			</el-col><!-- 快捷菜单end -->
	
			<!-- 日历信息 -->
			<el-col :span="12" style="margin-top: 10px;">
				<el-card class="box-card course">
					<div slot="header" class="clearfix">
						<span>现有教学班</span>
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
			<el-col :span="12" style="margin-top: 10px;">
				<el-card class="box-card notice">
					<div slot="header" class="clearfix">
						<span>现有课程</span>
					</div>
						<el-table :data="courses" style="width: 100%" size="small">
							<el-table-column type="index" label="序号" width="70" align="center" >
								<template slot-scope="scope">
								{{ scope.$index + 1 }}
								</template>
							</el-table-column>
							<el-table-column prop="name" label="课程名称" >
							</el-table-column>
							<el-table-column prop="courseId" label="课程编号" sortable>
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
				courses: [],
				teachingclasses: [],
			}
		},
		created() {
			this.fetchCourses();
		},
		methods: {
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
				try {
				const response = await axios.get('/findAllTeachingclass'); // 假设你使用的是 axios
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
		},
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
		top: 150px;
		left: 50%;
	}
	
	.person ul li {
		padding: 0;
		line-height: 30px;
		list-style: none;
		font-size: 20px;
		top:100px;
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
	
	
	
	.el-calendar__body {
		padding: 0px;
	}
	.course{
		height: 100%;
	}
	
	/* 通知公告样式 */
	.notice{
		height: 630px;
	}
	.notice ul li {
		list-style: none;
		height: 40px;
		line-height: 40px;
		border-bottom: 1px dashed gray;
		font-size: 13px;
		
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