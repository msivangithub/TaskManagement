import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { format } from 'date-fns';
import { ArrowLeft, Send } from 'lucide-react';
import { v4 as uuidv4 } from 'uuid';
import { store, Task, Comment, TaskStatus } from '@/lib/store';
import { cn } from '@/lib/utils';

export default function TaskDetails() {
  const { id } = useParams();
  const navigate = useNavigate();
  const currentUser = store.getCurrentUser();
  const users = store.getUsers();

  const [task, setTask] = useState<Task | null>(null);
  const [comments, setComments] = useState<Comment[]>([]);
  const [newComment, setNewComment] = useState('');

  useEffect(() => {
    if (id) {
      const allTasks = store.getTasks();
      const found = allTasks.find(t => t.id === id);
      if (found) {
        setTask(found);
        setComments(store.getComments(id));
      } else {
        navigate('/tasks');
      }
    }
  }, [id, navigate]);

  if (!task) return null;

  const handleStatusChange = (newStatus: TaskStatus) => {
    const updatedTask = { ...task, status: newStatus };
    setTask(updatedTask);
    const allTasks = store.getTasks().map(t => t.id === task.id ? updatedTask : t);
    store.saveTasks(allTasks);
  };

  const handleAddComment = (e: React.FormEvent) => {
    e.preventDefault();
    if (!newComment.trim() || !currentUser || !id) return;

    const comment: Comment = {
      id: uuidv4(),
      taskId: id,
      userId: currentUser.id,
      content: newComment,
      createdAt: new Date().toISOString(),
    };

    store.saveComment(comment);
    setComments([...comments, comment]);
    setNewComment('');
  };

  const assignee = users.find(u => u.id === task.assigneeId);
  const creator = users.find(u => u.id === task.creatorId);

  return (
    <div className="max-w-4xl mx-auto space-y-6">
      <button 
        onClick={() => navigate('/tasks')}
        className="flex items-center text-sm font-medium text-slate-500 hover:text-slate-900 transition-colors"
      >
        <ArrowLeft className="w-4 h-4 mr-1" />
        Back to Tasks
      </button>

      <div className="bg-white rounded-xl shadow-sm border border-slate-200 p-6 sm:p-8">
        <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4 mb-6">
          <h1 className="text-2xl font-bold text-slate-900">{task.title}</h1>
          
          <select
            value={task.status}
            onChange={(e) => handleStatusChange(e.target.value as TaskStatus)}
            className={cn(
              "pl-3 pr-8 py-1.5 rounded-full text-sm font-medium appearance-none cursor-pointer border-0 ring-1 ring-inset",
              task.status === 'Completed' ? 'bg-emerald-50 text-emerald-800 ring-emerald-200' : 
              task.status === 'In Progress' ? 'bg-blue-50 text-blue-800 ring-blue-200' : 
              'bg-slate-50 text-slate-800 ring-slate-200'
            )}
          >
            <option value="Pending">Pending</option>
            <option value="In Progress">In Progress</option>
            <option value="Completed">Completed</option>
          </select>
        </div>

        <div className="prose prose-slate max-w-none text-slate-600 text-sm mb-8">
          {task.description.split('\n').map((para, i) => (
            <p key={i}>{para}</p>
          ))}
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-3 gap-6 pt-6 border-t border-slate-100">
          <div>
            <span className="block text-xs font-medium text-slate-500 uppercase tracking-wider">Assignee</span>
            <span className="block mt-1 text-sm text-slate-900 font-medium">{assignee?.name || 'Unassigned'}</span>
          </div>
          <div>
            <span className="block text-xs font-medium text-slate-500 uppercase tracking-wider">Created By</span>
            <span className="block mt-1 text-sm text-slate-900 font-medium">{creator?.name || 'Unknown'}</span>
          </div>
          <div>
            <span className="block text-xs font-medium text-slate-500 uppercase tracking-wider">Due Date</span>
            <span className="block mt-1 text-sm text-slate-900 font-medium">{format(new Date(task.dueDate), 'MMM d, yyyy')}</span>
          </div>
        </div>
      </div>

      <div className="bg-white rounded-xl shadow-sm border border-slate-200 p-6 sm:p-8">
        <h2 className="text-lg font-semibold text-slate-900 mb-6">Comments</h2>
        
        <div className="space-y-6 mb-8">
          {comments.length === 0 ? (
            <p className="text-sm text-slate-500 italic">No comments yet.</p>
          ) : (
            comments.map(comment => {
              const user = users.find(u => u.id === comment.userId);
              return (
                <div key={comment.id} className="flex gap-4">
                  <div className="w-10 h-10 rounded-full bg-slate-100 border border-slate-200 flex items-center justify-center flex-shrink-0 text-slate-600 font-bold">
                    {user?.name.charAt(0) || '?'}
                  </div>
                  <div>
                    <div className="flex items-baseline gap-2">
                      <span className="text-sm font-medium text-slate-900">{user?.name || 'Unknown User'}</span>
                      <span className="text-xs text-slate-400">{format(new Date(comment.createdAt), 'MMM d, h:mm a')}</span>
                    </div>
                    <p className="text-sm text-slate-700 mt-1">{comment.content}</p>
                  </div>
                </div>
              );
            })
          )}
        </div>

        <form onSubmit={handleAddComment} className="flex gap-4">
          <input
            type="text"
            value={newComment}
            onChange={(e) => setNewComment(e.target.value)}
            placeholder="Add a comment..."
            className="flex-1 border border-slate-300 rounded-lg px-4 py-2 text-sm focus:ring-blue-500 focus:border-blue-500"
          />
          <button
            type="submit"
            disabled={!newComment.trim()}
            className="flex items-center gap-2 px-4 py-2 bg-slate-900 text-white rounded-lg hover:bg-slate-800 disabled:opacity-50 disabled:cursor-not-allowed font-medium text-sm transition-colors"
          >
            <Send className="w-4 h-4" />
            <span className="hidden sm:inline">Send</span>
          </button>
        </form>
      </div>
    </div>
  );
}
