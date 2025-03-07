<template>
	<div>
		<el-row>
			<!-- 操作栏 -->
			<el-col :span="24">
				<el-card class="box-card">
					<el-input v-model="studentId" placeholder="请输入学生学号" size="small" class="filter-input"></el-input>
					<el-button type="primary" icon="el-icon-search" size="small" plain @click="search">搜索</el-button>
					<el-input v-model="studentName" placeholder="请输入学生姓名" size="small" class="filter-input1"></el-input>
					<el-button type="primary" icon="el-icon-search" size="small" plain @click="searchByName">搜索</el-button>
					
					<el-button type="primary" icon="el-icon-refresh-right" size="small" @click="reset" class="reset">重置</el-button><br />
					</el-card>
					
				
		
			</el-col>
			<!-- 数据列表部分 -->
			<el-card class="box-card data">
				<el-table :data="tableData" style="width: 100%" size="small">
					<el-table-column type="index" label="序号" width="70" align="center" >
						<template slot-scope="scope">
						{{ scope.$index + 1 }}
						</template>
					</el-table-column>
					<el-table-column prop="studentId" label="学号" width="130" sortable>
					</el-table-column>
					<el-table-column prop="studentName" label="姓名" width="130">
					</el-table-column>
					<el-table-column prop="teachingclassId" label="教学班" width="130" sortable>
					</el-table-column>
					<el-table-column prop="course" label="课程" width="130">
					</el-table-column>
					<el-table-column prop="dailyscore" label="平时成绩">
					</el-table-column>
					<el-table-column prop="midtermscore" label="期中成绩">
					</el-table-column>
					<el-table-column prop="experimentscore" label="实验成绩">
					</el-table-column>
					<el-table-column prop="finalscore" label="期末成绩">
					</el-table-column>
					<el-table-column prop="totalscore" label="总成绩" sortable>
					</el-table-column>
				</el-table>
			</el-card>
		</el-row>
		
		
	</div>
</template>

<script>
	import axios from "axios";
	export default {
		data() {
			return {
				tableData: [],
				coursesMap: [],
				studentMap:[],
				
				studentId: null, // 初始化为空字符串
				teachingclassId: null,
				studentName: null, // 初始化为空字符串
				dialogFormVisible: false,
				dialogFormVisibleEdit:false,
				/* 表单数据 */
				formData: {
					id:'',
					name: '',
					telephone: '',
					address: '',
					profile: ''
				},
			}
		},
		created() {
			this.fetchCoursesMap();
			this.fetchStudentMap();
			this.search();
		},
		
		
		methods: {
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
				
			},
			async fetchStudentMap(){
				try {
					const response = await axios.get('/findStudentName');
					if (response.data.code === 200) {
					this.studentMap = response.data.data;
					} else {
					this.$message.error('信息获取失败');
					}
				} catch (error) {
					console.error('获取信息失败:', error);
					this.$message.error('获取信息失败');
				}
				
			},
			
			
			async search() {
				try {
				const params = {
					studentId: this.studentId || null, // 如果 studentId 为空字符串或未定义，则发送 null
					teachingclassId: this.teachingclassId || '',
					};
				const response = await axios.get('/selectScoresByIds', { params });
				if (response.data.code === 200) {
					this.tableData = response.data.data;
					this.tableData.forEach(item => {
							item.course = this.coursesMap[item.teachingclassId] || '未知课程';
							});
					this.tableData.forEach(item => {
							item.studentName = this.studentMap[item.studentId];
							
							});
						
				} else {
					this.$message.error('未搜索到成绩');
				}
				} catch (error) {
				console.error('获取成绩失败:', error);
				this.$message.error('获取成绩失败');
				}

				},
			reset() {
					// 重置搜索框和表格数据
					this.studentId = null;
					this.studentName = null;
					this.search();
				},
			
			async searchByName() {
				try {
					// 创建反向映射
					const reverseStudentMap = Object.entries(this.studentMap).reduce((acc, [name, id]) => {
						acc[id] = name;
						return acc;
					}, {});
				const params = {
					studentId: reverseStudentMap[this.studentName] || null, // 如果 studentId 为空字符串或未定义，则发送 null
					teachingclassId: this.teachingclassId || '',
					};
				const response = await axios.get('/selectScoresByIds', { params });
				if (response.data.code === 200) {
					this.tableData = response.data.data;
					this.tableData.forEach(item => {
							item.course = this.coursesMap[item.teachingclassId] || '未知课程';
							});
					this.tableData.forEach(item => {
							item.studentName = this.studentMap[item.studentId];
							
							});
						
				} else {
					this.$message.error('未搜索到成绩');
				}
				} catch (error) {
				console.error('获取成绩失败:', error);
				this.$message.error('获取成绩失败');
				}
			
				},
				
				
				
			
			
		}

	}
</script>

<style scoped>
	/* 操作框样式 */
	.box-card {
		margin: 5px;
	}

	/* 搜索框样式 */
	.filter-input {
		width: 200px;
		margin-right: 8px;

	}
	.filter-input1{
		width: 200px;
		margin-right: 8px;
		margin-left: 20px;
		margin-top: 10px;
	}
	
	.reset{
		margin-left: 480px;
	}

	/* 按钮样式 */
	.button {
		margin-top: 10px;
	}


	/* 设置表格行间距 */
	/deep/.el-table td,
	.el-table th {
		padding: 5px 0 !important;
	}

	/* 分页插件样式 */
	.el-pagination {
		margin: 10px;
		float: right;
	}
</style>