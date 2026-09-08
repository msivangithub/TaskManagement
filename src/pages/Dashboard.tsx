import { useMemo } from 'react';
import { Link } from 'react-router-dom';
import { format } from 'date-fns';
import { CheckCircle2, Clock, AlertCircle } from 'lucide-react';
import { store } from '@/lib/store';

export default function Dashboard() {
  const tasks = store.getTasks();
  const currentUser = store.getCurrentUser();

  const stats = useMemo(() => {
    const userTasks = currentUser?.role === 'Admin' 
      ? tasks 
      : tasks.filter(t => t.assigneeId === currentUser?.id || t.creatorId === currentUser?.id);

    return {
      total: userTasks.length,
      completed: userTasks.filter(t => t.status === 'Completed').length,
      pending: userTasks.filter(t => t.status === 'Pending').length,
      inProgress: userTasks.filter(t => t.status === 'In Progress').length,
      recent: userTasks.slice(0, 5)
    };
  }, [tasks, currentUser]);

  return (
    <div className="space-y-6 max-w-5xl mx-auto">
      <div>
        <h1 className="text-2xl font-bold text-slate-900">Dashboard</h1>
        <p className="mt-1 text-sm text-slate-500">
          Welcome back, {currentUser?.name}. Here's what's happening.
        </p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div className="bg-white rounded-xl shadow-sm border border-slate-200 p-6 flex items-start gap-4">
          <div className="p-3 bg-blue-50 text-blue-600 rounded-lg">
            <Clock className="w-6 h-6" />
          </div>
          <div>
            <p className="text-sm font-medium text-slate-500">In Progress</p>
            <p className="text-2xl font-bold text-slate-900">{stats.inProgress}</p>
          </div>
        </div>

        <div className="bg-white rounded-xl shadow-sm border border-slate-200 p-6 flex items-start gap-4">
          <div className="p-3 bg-amber-50 text-amber-600 rounded-lg">
            <AlertCircle className="w-6 h-6" />
          </div>
          <div>
            <p className="text-sm font-medium text-slate-500">Pending</p>
            <p className="text-2xl font-bold text-slate-900">{stats.pending}</p>
          </div>
        </div>

        <div className="bg-white rounded-xl shadow-sm border border-slate-200 p-6 flex items-start gap-4">
          <div className="p-3 bg-emerald-50 text-emerald-600 rounded-lg">
            <CheckCircle2 className="w-6 h-6" />
          </div>
          <div>
            <p className="text-sm font-medium text-slate-500">Completed</p>
            <p className="text-2xl font-bold text-slate-900">{stats.completed}</p>
          </div>
        </div>
      </div>

      <div className="bg-white rounded-xl shadow-sm border border-slate-200 overflow-hidden">
        <div className="px-6 py-5 border-b border-slate-200 flex justify-between items-center">
          <h2 className="text-lg font-semibold text-slate-900">Recent Tasks</h2>
          <Link to="/tasks" className="text-sm font-medium text-blue-600 hover:text-blue-700">
            View all
          </Link>
        </div>
        <div className="divide-y divide-slate-200">
          {stats.recent.length === 0 ? (
            <div className="p-6 text-center text-slate-500 text-sm">No tasks found.</div>
          ) : (
            stats.recent.map((task) => (
              <Link 
                key={task.id} 
                to={`/tasks/${task.id}`}
                className="block hover:bg-slate-50 transition-colors p-6"
              >
                <div className="flex items-center justify-between">
                  <div>
                    <h3 className="text-sm font-medium text-slate-900">{task.title}</h3>
                    <p className="text-sm text-slate-500 mt-1 line-clamp-1">{task.description}</p>
                  </div>
                  <div className="ml-4 flex flex-col items-end">
                    <span className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium
                      ${task.status === 'Completed' ? 'bg-emerald-100 text-emerald-800' : 
                        task.status === 'In Progress' ? 'bg-blue-100 text-blue-800' : 
                        'bg-slate-100 text-slate-800'}`}
                    >
                      {task.status}
                    </span>
                    <span className="text-xs text-slate-400 mt-2">
                      Due: {format(new Date(task.dueDate), 'MMM d, yyyy')}
                    </span>
                  </div>
                </div>
              </Link>
            ))
          )}
        </div>
      </div>
    </div>
  );
}
