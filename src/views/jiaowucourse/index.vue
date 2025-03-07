<template>
	<div>
		<el-row>
			
			<!-- 数据列表部分 -->
			<el-card class="box-card data">
				<el-button type="primary" size="small" icon="el-icon-plus" class="button" @click="handleCreate()"
					plain>新增课程</el-button>
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
					<el-table-column label="操作" align="center" width="180px">
						<template slot-scope="scope">
							<el-button type="primary" size="small" icon="el-icon-edit" class="button" @click="handleUpdate(scope.row)"  plain>编辑
							</el-button>
							<el-button type="danger" size="small" icon="el-icon-delete" class="button" plain @click="handleDelete(scope.row)">删除
							</el-button>
						</template>
					</el-table-column>
				</el-table>
				
		
			</el-card>
		</el-row>
		<!--删除确认弹框-->
		<div class="delete-form">
			<el-dialog title="确定要删除该课程吗？" :visible.sync="dialogFormVisibleDelete" @close='closeDialogDelete'>
				<div slot="footer" class="dialog-footer">
					<el-button @click="closeDelete()">取 消</el-button>
					<el-button type="primary" @click="deleteCourse()">确 定</el-button>
				</div>
			</el-dialog>
		</div>
		<!-- 添加弹框插件 -->
		<div class="add-form">
			<el-dialog title="新增课程" :visible.sync="dialogFormVisible" >
				<el-row>
					<el-col :span="24">
						<el-form :rules="rules" ref="formData">
							<el-form-item label="课程名称" prop="course" label-position="right" label-width="100px">
								<el-input v-model="courseName"></el-input> <!-- 直接绑定到 courseName -->
							</el-form-item>
						</el-form>
					</el-col>

				</el-row>
				<div slot="footer" class="dialog-footer">
					<el-button @click="dialogFormVisible = false">取 消</el-button>
					<el-button type="primary" @click="addCourse()">确 定</el-button>
				</div>
			</el-dialog>
		</div>
		
		<!-- 编辑修改框 -->
		<div class="update-form">
			<el-dialog title="修改课程信息" :visible.sync="dialogFormVisibleEdit">
				<el-row>
					<el-col :span="24">
						<el-form :model="currentCourse" :rules="rules" ref="courses">
							
							<el-form-item label="课程名称" prop="name" label-position="right" label-width="100px">
								<el-input v-model="currentCourse.name"></el-input>
							</el-form-item>
							<el-form-item label="课程编号" prop="courseId" label-position="right" label-width="100px">
								<el-input v-model="currentCourse.courseId" disabled></el-input>
							</el-form-item>
							
						</el-form>
					</el-col>
		
				</el-row>
		
				<div slot="footer" class="dialog-footer">
					<el-button @click="dialogFormVisibleEdit = false">取 消</el-button>
					<el-button type="primary" @click="editCourse()">确 定</el-button>
					
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
				courses: [],
				currentCourse: {},
				dialogFormVisible: false,
				dialogFormVisibleEdit:false,
				courseName:null,
				dialogFormVisibleDelete:false,
				
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
					name: [{
						required: true,
						message: '请输入课程名称',
						trigger: 'blur'
					}],
					course: [{
						required: true,
						message: '请输入课程名称',
						trigger: 'blur'
					}],

				}
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
			},
			
			async addCourse(){
				try {
				
				const response = await axios.post(`/insertCourse?name=${this.courseName}`); // 假设你使用的是 axios
				if (response.data.code === 200) {
					this.$message.success('添加课程成功');
				} else {
					this.$message.error('添加课程失败，课程名可能重复');
				}
				} catch (error) {
				console.error('添加课程失败:', error);
				this.$message.error('添加课程失败');
				}
				this.courseName=null;
				this.fetchCourses();
				this.dialogFormVisible = false;
			},
			
			/* 弹出添加窗口 */
			handleCreate() {
				this.dialogFormVisible = true
			},
			/* 弹出修改窗口 */
			handleUpdate(course) {
				this.currentCourse = { ...course};
				this.dialogFormVisibleEdit = true;
			},
			async editCourse(){
				try {
				
				const response = await axios.post(`/updateCourse?newName=${this.currentCourse.name}&courseId=${this.currentCourse.courseId}`); // 假设你使用的是 axios
				if (response.data.code === 200) {
					this.$message.success('修改成功');
				} else {
					this.$message.error('修改失败，课程名可能重复');
				}
				} catch (error) {
				console.error('修改失败:', error);
				this.$message.error('修改失败');
				}
				this.currentCourse = {};
				this.fetchCourses();
				this.dialogFormVisibleEdit = false;
			},
			
			/*删除窗口*/
			handleDelete(course){
				this.currentCourse = { ...course};
				this.dialogFormVisibleDelete = true;
			},
			closeDelete(){
				
				this.closeDialogDelete();
				
				this.currentCourse={};
			},
			closeDialogDelete() {
					this.dialogFormVisibleDelete = false
				},
			
			
			async deleteCourse(){
				try {
				const response = await axios.delete(`/deleteCourse?name=${this.currentCourse.name}`); // 假设你使用的是 axios
				if (response.data.code === 200) {
					this.$message.success('删除课程成功');
				} else {
					this.$message.error('删除课程失败');
				}
				} catch (error) {
				console.error('删除课程失败:', error);
				this.$message.error('删除课程失败');
				}
				
				this.currentCourse={};
				this.fetchCourses();
				this.dialogFormVisibleDelete = false;
				
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
		padding: 5px 0 !important;
	}

	
</style>