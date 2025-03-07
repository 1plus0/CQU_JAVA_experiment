<template>
	<div>
		<el-row>
			<!-- 操作栏 -->
			<!-- 数据列表部分 -->
			<el-card class="box-card data">
				<el-table :data="score" style="width: 100%" size="small">
					<el-table-column type="index" label="序号" width="70" align="center" >
						<template slot-scope="scope">
						{{ scope.$index + 1 }}
						</template>
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
					<el-table-column label="" align="center" width="100px">
						<template slot-scope="">
							<el-button type="primary" size="small" icon="el-icon-edit" class="button" @click="handleUpdate()"  plain>申诉
							</el-button>
						</template>
					</el-table-column>
				</el-table>
			</el-card>
		</el-row>
		<!-- 申诉框 -->
		<div class="update-form">
			<el-dialog title="您的申诉已提交!" :visible.sync="dialogFormVisibleEdit">
				<div slot="footer" class="dialog-footer">
					<el-button type="primary" @click="dialogFormVisibleEdit = false">确 定</el-button>
				</div>
			</el-dialog>
		</div>
		
		
	</div>
	
</template>

<script>
	import axios from "axios";
	export default {
		data() {
			return {
				score:[],
				user: null,
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
				/*数据校验 */
				rules: {
					college: [{
						required: true,
						message: '请输入学院名称',
						trigger: 'blur'
					}],
					telephone: [{
						required: true,
						message: '请输入联系号码',
						trigger: 'blur'
					}],
					address: [{
						required: true,
						message: '请输入学院地址',
						trigger: 'blur'
					}],

				}
			}
		},
		created() {
			const user = localStorage.getItem('user');
			if (user) {
				this.user = JSON.parse(user);
			}
			this.fetchCourses();
		},
		methods: {
			/* 弹出添加窗口 */
			handleCreate() {
				this.dialogFormVisible = true
			},
			/* 弹出申诉窗口 */
			handleUpdate() {
				this.dialogFormVisibleEdit = true
			},
			async fetchCourses() {
				try {
				const response = await axios.get(`/selectScoreById/${this.user.id}`); // 假设你使用的是 axios
				if (response.data.code === 200) {
					this.score = response.data.data;
					await this.fetchMap();
				} else {
					this.$message.error('加载成绩失败');
				}
				} catch (error) {
				console.error('获取成绩失败:', error);
				this.$message.error('获取成绩失败');
				}
			},
			async fetchMap(){
				try {
					const response =  await axios.get('/selectCourseByTc');
					if (response.data.code === 200) {
						const namesMap = response.data.data; // 这是您从后端获得的 Map
						this.score.forEach(classItem => {
						this.$set(classItem, 'course', namesMap[classItem.teachingclassId] || '未知课程');
						
						});
					} else {
						this.$message.error('加载课程名称失败');
					}
				} catch (error) {
					console.error('获取课程名称失败:', error);
					this.$message.error('获取课程名称失败');
				}
			}
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
		margin-right: 20px;
	}

	/* 按钮样式 */
	.button {
		margin-top: 10px;
	}


	/* 设置表格行间距 */
	/deep/.el-table td,
	.el-table th {
		padding: 15px 0 !important;
	}

	/* 分页插件样式 */
	.el-pagination {
		margin: 10px;
		float: right;
	}
</style>