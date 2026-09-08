export type TaskStatus = 'Pending' | 'In Progress' | 'Completed';

export interface User {
  id: string;
  name: string;
  email: string;
  role: string;
  avatar?: string;
}

export interface Comment {
  id: string;
  taskId: string;
  userId: string;
  content: string;
  createdAt: string;
}

export interface Task {
  id: string;
  title: string;
  description: string;
  status: TaskStatus;
  assigneeId?: string;
  creatorId: string;
  dueDate: string;
  createdAt: string;
  attachments?: string[];
}

const mockUsers: User[] = [
  { id: '1', name: 'Admin User', email: 'admin@myaccounts.in', role: 'Admin' },
  { id: '2', name: 'Sivan', email: 'msivan@myaccounts.in', role: 'Employee' },
];

const mockTasks: Task[] = [
  {
    id: 't1',
    title: 'Setup React Project',
    description: 'Port the Android application to a React web app.',
    status: 'In Progress',
    assigneeId: '2',
    creatorId: '1',
    dueDate: new Date(Date.now() + 86400000 * 2).toISOString(),
    createdAt: new Date().toISOString(),
  }
];

export const store = {
  getUsers: (): User[] => {
    const users = localStorage.getItem('users');
    return users ? JSON.parse(users) : mockUsers;
  },
  saveUsers: (users: User[]) => {
    localStorage.setItem('users', JSON.stringify(users));
  },
  getTasks: (): Task[] => {
    const tasks = localStorage.getItem('tasks');
    return tasks ? JSON.parse(tasks) : mockTasks;
  },
  saveTasks: (tasks: Task[]) => {
    localStorage.setItem('tasks', JSON.stringify(tasks));
  },
  getComments: (taskId: string): Comment[] => {
    const comments = localStorage.getItem('comments');
    if (!comments) return [];
    const parsed: Comment[] = JSON.parse(comments);
    return parsed.filter(c => c.taskId === taskId);
  },
  saveComment: (comment: Comment) => {
    const comments = localStorage.getItem('comments');
    const parsed: Comment[] = comments ? JSON.parse(comments) : [];
    parsed.push(comment);
    localStorage.setItem('comments', JSON.stringify(parsed));
  },
  getCurrentUser: (): User | null => {
    const user = localStorage.getItem('currentUser');
    return user ? JSON.parse(user) : null;
  },
  setCurrentUser: (user: User | null) => {
    if (user) {
      localStorage.setItem('currentUser', JSON.stringify(user));
    } else {
      localStorage.removeItem('currentUser');
    }
  }
};
