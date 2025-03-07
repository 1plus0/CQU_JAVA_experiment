import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)



const routes = [{
		path: "/",
		name: '用户登录',
		component: () => import('../views/login')
	},
	{
		path: "/teacherhome",
		name: '首页',
		redirect: '/teacherwelcome',
		component: () => import('../views/teacherhome'),
		
		children:[
			{
				path:'/teacherwelcome',
				name:'教师欢迎页面',
				component:() => import('../views/teacherwelcome')
			},
			
			{
				path:'/teacherscore',
				name:'成绩管理',
				component:() => import('../views/teacherscore'),
				meta:{
					title:'成绩管理'
				}
			},
			{
				path:'/teacherdata',
				name:'数据分析',
				component:() => import('../views/teacherdata'),
				meta:{
					title:'数据分析'
				}
			},
		]
	},
	{
		path: "/studenthome",
		name: '首页',
		redirect: '/studentwelcome',
		component: () => import('../views/studenthome'),
		children:[
			{
				path:'/studentwelcome',
				name:'学生欢迎页面',
				component:() => import('../views/studentwelcome')
			},
			{
				path:'/studentscore',
				name:'成绩查看',
				component:() => import('../views/studentscore'),
				meta:{
					title:'成绩查看'
				}
			},
		]
	},
	{
		path: "/jiaowuhome",
		name: '首页',
		redirect: '/jiaowuwelcome',
		component: () => import('../views/jiaowuhome'),
		children:[
			{
				path:'/jiaowuwelcome',
				name:'教师欢迎页面',
				component:() => import('../views/jiaowuwelcome')
			},
			{
				path:'/jiaowucourse',
				name:'教务课程管理',
				component:() => import('../views/jiaowucourse')
			},
			{
				path:'/jiaowumanager',
				name:'成绩管理',
				component:() => import('../views/jiaowumanager'),
				meta:{
					title:'成绩管理'
				}
			},
			{
				path:'/jiaowuscore',
				name:'成绩管理',
				component:() => import('../views/jiaowuscore'),
				meta:{
					title:'成绩管理'
				}
			},
			{
				path:'/jiaowudata',
				name:'数据分析',
				component:() => import('../views/jiaowudata'),
				meta:{
					title:'数据分析'
				}
			},
		]
	},

]
const router = new VueRouter({
	routes,
	mode: 'history',
	
});

router.beforeEach((to, from, next) => {
	const isLogged = localStorage.getItem('user') !== null;
	if (!isLogged && to.path !== '/' && to.path !== '/login') {
	next({ path: '/' });
	} else {
	next();
	}
});

export default router
